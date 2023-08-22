package ru.otus.livescore

import ru.otus.otuskotlin.livescore.common.models.*
import ru.otus.otuskotlin.livescore.common.repo.DbMatchIdRequest
import ru.otus.otuskotlin.livescore.common.repo.IMatchesRepository
import kotlin.test.Test
import kotlin.test.assertEquals
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
abstract class RepoMatchReadTest {
    abstract val repo: IMatchesRepository
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readMatch(DbMatchIdRequest(readSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(readSucc, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readMatch(DbMatchIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }
    companion object : BaseInitMatches("read") {
        override val initObjects: List<LsMatch> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = LsMatchId("ad-repo-read-notFound")

    }
}