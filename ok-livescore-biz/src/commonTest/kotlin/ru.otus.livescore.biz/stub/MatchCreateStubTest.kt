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

@OptIn(ExperimentalCoroutinesApi::class)
class MatchCreateStubTest {
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
    fun create() = runTest {

        val ctx = LsContext(
            command = LsCommand.CREATE,
            state = LsState.NONE,
            workMode = LsWorkMode.STUB,
            stubCase = LsStubs.SUCCESS,
            matchRequest = LsMatch(
                id = id,
                participant1 = participant1,
                participant2 = participant2,
                score1 = score1,
                score2 = score2,
                eventId = eventId,
                status = matchStatus,
                court = court
            )
        )

        processor.exec(ctx)
        assertEquals(LsMatchStub.get().id, ctx.matchResponse.id)
        assertEquals(participant1, ctx.matchResponse.participant1)
        assertEquals(participant2, ctx.matchResponse.participant2)
        assertEquals(score1, ctx.matchResponse.score1)
        assertEquals(score2, ctx.matchResponse.score2)
        assertEquals(matchStatus, ctx.matchResponse.status)
        assertEquals(court, ctx.matchResponse.court)
        assertEquals(eventId, ctx.matchResponse.eventId)
    }

    @Test
    fun badParticipant() = runTest {
        val ctx = LsContext(
            command = LsCommand.CREATE,
            state = LsState.NONE,
            workMode = LsWorkMode.STUB,
            stubCase = LsStubs.BAD_PARTICIPANT_NAME,
            matchRequest = LsMatch(
                id = id,
                participant1 = "",
                participant2 = participant2,
                score1 = score1,
                score2 = score2,
                eventId = eventId,
                status = matchStatus,
                court = court
        )
        )

        processor.exec(ctx)
        assertEquals(LsMatch(), ctx.matchResponse)
        assertEquals("participant1", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badScore() = runTest {
        val ctx = LsContext(
            command = LsCommand.CREATE,
            state = LsState.NONE,
            workMode = LsWorkMode.STUB,
            stubCase = LsStubs.BAD_SCORE,
            matchRequest = LsMatch(
                id = id,
                participant1 = participant1,
                participant2 = participant2,
                score1 = -1,
                score2 = score2,
                eventId = eventId,
                status = matchStatus,
                court = court
            )
        )

        processor.exec(ctx)
        assertEquals(LsMatch(), ctx.matchResponse)
        assertEquals("score1", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = LsContext(
            command = LsCommand.CREATE,
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
            command = LsCommand.CREATE,
            state = LsState.NONE,
            workMode = LsWorkMode.STUB,
            stubCase = LsStubs.BAD_ID,
            matchRequest = LsMatch(
                id = id,
                participant1 = participant1,
                participant2 = participant2,
                score1 = score1,
                score2 = score2,
                eventId = eventId,
                status = matchStatus,
                court = court
            )
        )

        processor.exec(ctx)
        assertEquals(LsMatch(), ctx.matchResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)

    }
}