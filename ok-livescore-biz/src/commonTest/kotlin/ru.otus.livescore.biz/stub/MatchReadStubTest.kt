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
class MatchReadStubTest {
    private val processor = LSMatchProcessor(LsCorSettings())

    val id = LsMatchId("666")

    @Test
    fun read() = runTest {
        val ctx = LsContext(
            command = LsCommand.READ,
            state = LsState.NONE,
            workMode = LsWorkMode.STUB,
            stubCase = LsStubs.SUCCESS,
            matchRequest = LsMatch(
                id = id,
            )
        )

        processor.exec(ctx)
        with(LsMatchStub.get()) {
            assertEquals(id, ctx.matchResponse.id)
            assertEquals(participant1, ctx.matchResponse.participant1)
            assertEquals(participant2, ctx.matchResponse.participant2)
            assertEquals(score1, ctx.matchResponse.score1)
            assertEquals(score2, ctx.matchResponse.score2)
            assertEquals(court, ctx.matchResponse.court)
            assertEquals(status, ctx.matchResponse.status)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = LsContext(
            command = LsCommand.READ,
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
            command = LsCommand.READ,
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
            command = LsCommand.READ,
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