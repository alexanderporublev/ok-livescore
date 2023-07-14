package ru.otus.livescore.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ru.otus.api.v1.models.*
import ru.otus.livescore.blackbox.fixture.client.Client

suspend fun Client.createMatch(match: MatchCreateObject = someCreateMatch): MatchResponseObject = createMatch(match) {
    it should haveSuccessResult
    it.match shouldNotBe null
    it.match?.apply {
        particapant1 shouldBe match.particapant1
        particapant2 shouldBe match.particapant2
        score1 shouldBe  match.score1
        score2 shouldBe match.score2
        matchStatus shouldBe match.matchStatus
    }
    it.match!!
}

suspend fun <T> Client.createMatch(match: MatchCreateObject = someCreateMatch, block: (MatchCreateResponse) -> T): T =
    withClue("createMatch: $match") {
        val response = sendAndReceive(
            "match/create", MatchCreateRequest(
                requestType = "create",
                debug = debug,
                match = match
            )
        ) as MatchCreateResponse

        response.asClue(block)
    }
