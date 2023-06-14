package ru.otus.otuskotlin.livescore.mappers.v1

import kotlinx.datetime.toJavaInstant
import org.junit.Test
import ru.otus.api.v1.models.*
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.*
import ru.otus.otuskotlin.livescore.common.stubs.LsStubs
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.test.assertEquals
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.otuskotlin.marketplace.mappers.v1.*

class MapperTest {
    @Test
    fun fromTransport() {
        val req = MatchCreateRequest(
            requestId = "1234",
            debug = MatchDebug(
                mode = MatchRequestDebugMode.STUB,
                stub = MatchRequestDebugStubs.SUCCESS,
            ),
            match = MatchCreateObject(
                eventId = "0000",
                particapant1 = "Ivanov",
                particapant2 = "Petrov",
                score1 = 0,
                score2 = 0,
                court = "1",
                datetime = "2023-04-30T14:50",
                matchStatus = MatchStatus.INPROGRESS
            ),
        )

        val context = LsContext()
        context.fromTransport(req)

        assertEquals(LsStubs.SUCCESS, context.stubCase)
        assertEquals(LsWorkMode.STUB, context.workMode)
        assertEquals("Ivanov", context.matchRequest.participant1)
        assertEquals(LocalDateTime.parse( "2023-04-30T14:50"),  LocalDateTime.ofInstant(context.matchRequest.datetime.toJavaInstant(), ZoneOffset.UTC))
        assertEquals(LsMatchStatus.INPROGRESS, context.matchRequest.status)
    }

    @Test
    fun toTransport() {
        val context = LsContext(
            requestId = LsRequestId("1234"),
            command = LsCommand.CREATE,
            matchResponse = LsMatch(
                eventId = LsEventId("0000"),
                participant1 = "Ivanov",
                participant2 = "Petrov",
                score1 =-1,
                score2 =0,
                court = "central",

            ),
            errors = mutableListOf(
                LsError(
                    code = "err",
                    group = "request",
                    field = "score1",
                    message = "scores count can't be negative",
                )
            ),
            state = LsState.RUNNING,
        )

        val req = context.toTransportMatch() as MatchCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("Ivanov", req.ad?.particapant1)
        assertEquals(0, req.ad?.score2)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("score1", req.errors?.firstOrNull()?.field)
        assertEquals("scores count can't be negative", req.errors?.firstOrNull()?.message)
    }
}
