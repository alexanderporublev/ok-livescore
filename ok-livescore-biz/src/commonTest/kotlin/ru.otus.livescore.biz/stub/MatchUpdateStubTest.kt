package ru.otus.livescore.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.datetime.Instant

import ru.otus.livescore.biz.LSMatchProcessor
import ru.otus.livescore.stubs.LsMatchStub
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.LsCorSettings
import ru.otus.otuskotlin.livescore.common.models.*
import ru.otus.otuskotlin.livescore.common.stubs.LsStubs

class MatchUpdateStubTest {
    private val processor = LSMatchProcessor(LsCorSettings())

    val id = LsMatchId("666")
    val participant1 = "Hoblin"
    val participant2 = "Elph"
    val score1 = 4
    val score2 = 3
    val matchStatus = LsMatchStatus.INPROGRESS
    val eventId = LsEventId("777")
    val court = "1"

    @Test
    fun update() = runTest {

        val ctx = LsContext(
            command = LsCommand.UPDATE,
            state = LsState.NONE,
            workMode = LsWorkMode.STUB,
            stubCase = LsStubs.SUCCESS,
            matchRequest = LsMatch(
                id = id,
                score1 = score1,
                score2 = score2,
            )
        )

        processor.exec(ctx)
        assertEquals(LsMatchStub.get().id, ctx.matchResponse.id)
        assertEquals(score1, ctx.matchResponse.score1)
        assertEquals(score2, ctx.matchResponse.score2)
    }
    @Test
    fun badId() = runTest {
        val ctx = LsContext(
            command = LsCommand.UPDATE,
            state = LsState.NONE,
            workMode = LsWorkMode.STUB,
            stubCase = LsStubs.BAD_ID,
            matchRequest = LsMatch(
            )
        )
        processor.exec(ctx)
        assertEquals(LsMatch(), ctx.matchResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)

    }

    @Test
    fun databaseError() = runTest {
        val ctx = LsContext(
            command = LsCommand.UPDATE,
            state = LsState.NONE,
            workMode = LsWorkMode.STUB,
            stubCase = LsStubs.DB_ERROR,
            matchRequest = LsMatch(
                id = id,
            )
        )
        processor.exec(ctx)
        assertEquals(LsMatch(), ctx.matchResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = LsContext(
            command = LsCommand.UPDATE,
            state = LsState.NONE,
            workMode = LsWorkMode.STUB,
            stubCase = LsStubs.BAD_PARTICIPANT_NAME,
            matchRequest = LsMatch(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(LsMatch(), ctx.matchResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)

    }
}