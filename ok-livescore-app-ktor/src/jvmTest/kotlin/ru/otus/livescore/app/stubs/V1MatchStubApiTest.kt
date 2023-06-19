package ru.otus.otuskotlin.marketplace.app.ru.otus.livescore.app.stubs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.api.v1.models.*
import kotlin.test.assertEquals

class V1MatchStubApiTest {
    @Test
    fun create() = testApplication {
        val client = myClient()

        val response = client.post("/v1/match/create") {
            val requestObj = MatchCreateRequest(
                requestId = "12345",
                match = MatchCreateObject(
                    eventId = "666",
                    particapant1 = "Ivanov",
                    particapant2 = "Petrov",
                    score1 = 0,
                    score2 = 0,
                    court = "1",
                    datetime = "2023-04-30T14:50",
                    matchStatus = MatchStatus.INPROGRESS,
                ),
                debug = MatchDebug(
                    mode = MatchRequestDebugMode.STUB,
                    stub = MatchRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<MatchCreateResponse>()
        println(responseObj)
        assertEquals(200, response.status.value)
        assertEquals("667", responseObj.ad?.id)
    }

    @Test
    fun read() = testApplication {
        val client = myClient()

        val response = client.post("/v1/match/read") {
            val requestObj = MatchReadRequest(
                requestId = "12345",
                match = MatchReadObject("667"),
                debug = MatchDebug(
                    mode = MatchRequestDebugMode.STUB,
                    stub = MatchRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<MatchReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("667", responseObj.ad?.id)
    }

    @Test
    fun update() = testApplication {
        val client = myClient()

        val response = client.post("/v1/match/update") {
            val requestObj = MatchUpdateRequest(
                requestId = "12345",
                match = MatchUpdateObject(
                    id = "667",
                    particapant1 = "Ivanov",
                    particapant2 = "Petrov",
                    score1 = 6,
                    score2 = 4,
                    court = "1",
                    datetime = "2023-04-30T14:50",
                    matchStatus = MatchStatus.COMPLETED,

                    ),
                debug = MatchDebug(
                    mode = MatchRequestDebugMode.STUB,
                    stub = MatchRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<MatchUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("667", responseObj.ad?.id)
    }

    @Test
    fun delete() = testApplication {
        val client = myClient()

        val response = client.post("/v1/match/delete") {
            val requestObj = MatchDeleteRequest(
                requestId = "12345",
                match = MatchDeleteObject(
                    id = "667",
                ),
                debug = MatchDebug(
                    mode = MatchRequestDebugMode.STUB,
                    stub = MatchRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<MatchDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("667", responseObj.ad?.id)
    }

    @Test
    fun search() = testApplication {
        val client = myClient()

        val response = client.post("/v1/match/matches") {
            val requestObj = MatchesRequest(
                requestId = "12345",
                debug = MatchDebug(
                    mode = MatchRequestDebugMode.STUB,
                    stub = MatchRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<MatchesResponse>()
        assertEquals(200, response.status.value)
        assertEquals("s-666-01", responseObj.ads?.first()?.id)
    }


    private fun ApplicationTestBuilder.myClient() = createClient {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }
}
