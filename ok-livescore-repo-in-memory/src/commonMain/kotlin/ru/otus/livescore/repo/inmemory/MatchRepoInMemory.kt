package ru.otus.livescore.repo.inmemory
import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.otus.livescore.repo.inmemory.model.MatchEntity
import ru.otus.otuskotlin.livescore.common.helpers.errorRepoConcurrency
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import ru.otus.otuskotlin.livescore.common.models.*
import ru.otus.otuskotlin.livescore.common.repo.*
class MatchRepoInMemory(
    initObjects: List<LsMatch> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : IMatchesRepository {

    private val cache = Cache.Builder<String, MatchEntity>()
        .expireAfterWrite(ttl)
        .build()
    private val mutex: Mutex = Mutex()
    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(match: LsMatch) {
        val entity =  MatchEntity(match)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createMatch(rq: DbMatchRequest): DbMatchResponse {
        val key = randomUuid()
        val match = rq.match.copy(id = LsMatchId(key), lock = LsMatchLock(randomUuid()))
        val entity = MatchEntity(match)
        cache.put(key, entity)
        return DbMatchResponse(
            data = match,
            isSuccess = true,
        )
    }

    override suspend fun readMatch(rq: DbMatchIdRequest): DbMatchResponse {
        val key = rq.id.takeIf { it != LsMatchId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbMatchResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateMatch(rq: DbMatchRequest): DbMatchResponse {
        val key = rq.match.id.takeIf { it != LsMatchId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.match.lock.takeIf { it != LsMatchLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val newMatch = rq.match.copy(lock = LsMatchLock(randomUuid()))
        val entity = MatchEntity(newMatch)
        return mutex.withLock {
            val oldMatch = cache.get(key)
            when {
                oldMatch == null -> resultErrorNotFound
                oldMatch.lock != oldLock -> DbMatchResponse(
                    data = oldMatch.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(LsMatchLock(oldLock), oldMatch.lock?.let { LsMatchLock(it) }))
                )
                else -> {
                    cache.put(key, entity)
                    DbMatchResponse(
                        data = newMatch,
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun deleteMatch(rq: DbMatchIdRequest): DbMatchResponse {
        val key = rq.id.takeIf { it != LsMatchId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != LsMatchLock.NONE }?.asString() ?: return resultErrorEmptyLock
        return mutex.withLock {
            val oldMatch = cache.get(key)
            when{
                oldMatch == null ->  resultErrorNotFound
                oldMatch.lock != oldLock -> DbMatchResponse(
                    data = oldMatch.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(LsMatchLock(oldLock), oldMatch.lock?.let { LsMatchLock(it) }))
                )
                else -> {
                    cache.invalidate(key)
                    DbMatchResponse(
                        data = oldMatch.toInternal(),
                        isSuccess = true,
                    )
                }
            }
        }
    }

    companion object {
        val resultErrorEmptyId = DbMatchResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                LsError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorEmptyLock = DbMatchResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                LsError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbMatchResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                LsError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}