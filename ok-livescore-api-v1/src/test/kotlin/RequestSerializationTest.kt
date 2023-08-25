package ru.otus.otuskotlin.marketplace.api.v1

import ru.otus.api.v1.models.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = MatchCreateRequest(
        requestId = "123",
        debug = MatchDebug(
            mode = MatchRequestDebugMode.STUB,
            stub = MatchRequestDebugStubs.BAD_PARTICIPANT_NAME
        ),
        match = MatchCreateObject(
            eventId = "0",
            particapant1 = "Petrov",
            particapant2 = "Ivanov",
            score1 = 0,
            score2 = 0,
            court = "central",
            datetime = "2023-04-3014:50",//LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
            matchStatus = MatchStatus.INPROGRESS
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"particapant1\":\\s*\"Petrov\""))
        assertContains(json, Regex("\"particapant2\":\\s*\"Ivanov\""))
        assertContains(json, Regex("\"score1\":\\s*0"))
        assertContains(json, Regex("\"score2\":\\s*0"))
    }

    @Test
    fun deserialize() {
        println("deserialize")
        val json = apiV1Mapper.writeValueAsString(request)
        println(json)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as MatchCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"requestId": "123"}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, MatchCreateRequest::class.java)

        assertEquals("123", obj.requestId)
    }
}
