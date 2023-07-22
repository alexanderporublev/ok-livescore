package ru.otus.livescore.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import ru.otus.api.v1.models.*
import ru.otus.livescore.blackbox.fixture.client.Client

suspend fun Client.matches(id: String?): List<MatchResponseObject> = matches(id) {
    it should haveSuccessResult
    it.ads ?: listOf()
}

suspend fun <T> Client.matches(id: String?, block: (MatchesResponse) -> T): T =
    withClue("matchesV1: $id") {
        val response = sendAndReceive(
            "ad/matches",
            MatchesRequest(
                requestType = "matches",
                debug = debug,
                ad = MatchReadObject(id = id),
            )
        ) as MatchesResponse

        response.asClue(block)
    }
