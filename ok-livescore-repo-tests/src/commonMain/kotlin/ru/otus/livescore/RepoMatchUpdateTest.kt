package ru.otus.livescore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.livescore.common.models.*
import ru.otus.otuskotlin.livescore.common.repo.DbMatchRequest
import ru.otus.otuskotlin.livescore.common.repo.IMatchesRepository
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoMatchUpdateTest {
    abstract val repo: IMatchesRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = LsMatchId("ad-repo-update-not-found")
    protected val lockBad = LsMatchLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = LsMatchLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        LsMatch(
            id = updateSucc.id,
            participant1 = "Petrov",
            participant2 = "Titov",
            score1 = 4,
            score2 = 6,
            court = "1",
            status = LsMatchStatus.FINISHED,
            lock = initObjects.first().lock,
        )
    }

    private val reqUpdateNotFound = LsMatch(
        id = updateIdNotFound,
        participant1 = "Petrov",
        participant2 = "Titov",
        score1 = 4,
        score2 = 6,
        court = "1",
        status = LsMatchStatus.FINISHED,
        lock = initObjects.first().lock,
        )

    private val reqUpdateConc by lazy {
        LsMatch(
            id = updateConc.id,
            participant1 = "Petrov",
            participant2 = "Titov",
            score1 = 4,
            score2 = 6,
            court = "1",
            status = LsMatchStatus.FINISHED,
            lock = lockBad,
            )
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateMatch(DbMatchRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateMatch(DbMatchRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitMatches("update") {
        override val initObjects: List<LsMatch> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}