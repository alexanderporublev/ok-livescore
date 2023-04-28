package ru.otus.otuskotlin.marketplace.api.v1

import ru.otus.api.v1.models.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = MatchCreateResponse(
        requestId = "123",
        ad = MatchResponseObject(
            eventId = "0",
            particapant1 = "Petrov",
            particapant2 = "Ivanov",
            score1 = 0,
            score2 = 0,
            court = "central",
            datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
            matchStatus = MatchStatus.INPROGRESS
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"particapant1\":\\s*\"Petrov\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as MatchCreateResponse

        assertEquals(response, obj)
    }
}
