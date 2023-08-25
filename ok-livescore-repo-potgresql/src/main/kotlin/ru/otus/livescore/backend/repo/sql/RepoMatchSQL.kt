package ru.otus.livescore.backend.repo.sql

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otus.otuskotlin.livescore.common.helpers.asLsError
import ru.otus.otuskotlin.livescore.common.models.*
import ru.otus.otuskotlin.livescore.common.repo.*

class RepoMatchSQL (
    properties: SqlProperties,
    initObjects: Collection<LsMatch> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
    ) : IMatchesRepository {

    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
        }
        println(properties.url)
        Database.connect(
            properties.url, driver/*, properties.user, properties.password*/
        )

        transaction {
            if (properties.dropDatabase) SchemaUtils.drop(MatchTable)
            SchemaUtils.create(MatchTable)
            initObjects.forEach { createMatch(it) }
        }
    }

    private fun createMatch(match: LsMatch): LsMatch {
        val res = MatchTable.insert {
            to(it, match, randomUuid)
        }

        return MatchTable.from(res)
    }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbMatchResponse): DbMatchResponse =
        transactionWrapper(block) { DbMatchResponse.error(it.asLsError()) }

    override suspend fun createMatch(rq: DbMatchRequest): DbMatchResponse = transactionWrapper {
        DbMatchResponse.success(createMatch(rq.match))
    }

    private fun read(id: LsMatchId): DbMatchResponse {
        val res = MatchTable.select {
            MatchTable.id eq id.asString()
        }.singleOrNull() ?: return DbMatchResponse.errorNotFound
        return DbMatchResponse.success(MatchTable.from(res))
    }

    override suspend fun readMatch(rq: DbMatchIdRequest): DbMatchResponse = transactionWrapper { read(rq.id) }

    private fun update(
        id: LsMatchId,
        lock: LsMatchLock,
        block: (LsMatch) -> DbMatchResponse
    ): DbMatchResponse =
        transactionWrapper {
            if (id == LsMatchId.NONE) return@transactionWrapper DbMatchResponse.errorEmptyId

            val current = MatchTable.select { MatchTable.id eq id.asString() }
                .firstOrNull()
                ?.let { MatchTable.from(it) }

            when {
                current == null -> DbMatchResponse.errorNotFound
                current.lock != lock -> DbMatchResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }

    override suspend fun updateMatch(rq: DbMatchRequest): DbMatchResponse =
        update(rq.match.id, rq.match.lock) {
            MatchTable.update({ MatchTable.id eq rq.match.id.asString() }) {
                to(it, rq.match, randomUuid)
            }
            read(rq.match.id)
        }

    override suspend fun deleteMatch(rq: DbMatchIdRequest): DbMatchResponse = update(rq.id, rq.lock) {
        MatchTable.deleteWhere { MatchTable.id eq rq.id.asString() }
        DbMatchResponse.success(it)
    }

}