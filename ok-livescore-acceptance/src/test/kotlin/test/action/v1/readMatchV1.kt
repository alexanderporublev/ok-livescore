package ru.otus.livescore.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import ru.otus.api.v1.models.MatchReadObject
import ru.otus.api.v1.models.MatchReadRequest
import ru.otus.api.v1.models.MatchReadResponse
import ru.otus.api.v1.models.MatchResponseObject
import ru.otus.livescore.blackbox.test.action.beValidId
import ru.otus.livescore.blackbox.fixture.client.Client

suspend fun Client.readMatch(id: String?): MatchResponseObject = readMatch(id) {
    it should haveSuccessResult
    it.ad shouldNotBe null
    it.ad!!
}

suspend fun <T> Client.readMatch(id: String?, block: (MatchReadResponse) -> T): T =
    withClue("readMatchV1: $id") {
        id should beValidId

        val response = sendAndReceive(
            "match/read",
            MatchReadRequest(
                requestType = "read",
                debug = debug,
                match = MatchReadObject(id = id)
            )
        ) as MatchReadResponse

        response.asClue(block)
    }
