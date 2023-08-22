package ru.otus.livescore

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.livescore.common.models.*
import ru.otus.otuskotlin.livescore.common.repo.DbMatchRequest
import ru.otus.otuskotlin.livescore.common.repo.IMatchesRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoMatchCreateTest {
    abstract val repo: IMatchesRepository

    protected open val lockNew: LsMatchLock = LsMatchLock("20000000-0000-0000-0000-000000000002")

    private val createObj = LsMatch(
        participant1 = "Ivanov",
        participant2 = "Petrov",
        score1 = 0,
        score2 = 0,
        court = "1",
        eventId = LsEventId("999"),
        status = LsMatchStatus.INPROGRESS
    )

    @Test
    fun createSucces() = runRepoTest {
        val result = repo.createMatch(DbMatchRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: LsMatchId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.participant1, result.data?.participant1)
        assertEquals(expected.participant2, result.data?.participant2)
        assertEquals(expected.score1, result.data?.score1)
        assertEquals(expected.score2, result.data?.score2)
        assertNotEquals(LsEventId.NONE.asString(), result.data?.id?.asString())
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)

    }

    companion object : BaseInitMatches("create") {
        override val initObjects: List<LsMatch> = emptyList()
    }
}