package ru.otus.livescore

import ru.otus.otuskotlin.livescore.common.models.*
import ru.otus.otuskotlin.livescore.common.repo.DbMatchIdRequest
import ru.otus.otuskotlin.livescore.common.repo.IMatchesRepository
import kotlin.test.Test
import kotlin.test.assertEquals
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
abstract class RepoMatchDeleteTest {
    abstract val repo: IMatchesRepository
    protected open val deleteSucc = initObjects[0]
    protected open val deleteConc = initObjects[1]
    protected open val notFoundId = LsMatchId("match-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteMatch(DbMatchIdRequest(deleteSucc.id, lock = lockOld))

        assertEquals(true, result.isSuccess)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockOld, result.data?.lock)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readMatch(DbMatchIdRequest(notFoundId, lock = lockOld))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun deleteConcurrency() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteMatch(DbMatchIdRequest(deleteConc.id, lock = lockBad))

        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(lockOld, result.data?.lock)
    }
    companion object : BaseInitMatches("delete") {
        override val initObjects: List<LsMatch> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteConc"),
        )
    }
}