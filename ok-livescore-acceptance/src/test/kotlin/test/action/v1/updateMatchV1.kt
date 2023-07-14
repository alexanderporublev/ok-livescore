package ru.otus.livescore.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ru.otus.api.v1.models.*
import ru.otus.livescore.blackbox.test.action.beValidId
import ru.otus.livescore.blackbox.test.action.beValidLock
import ru.otus.livescore.blackbox.fixture.client.Client

suspend fun Client.updateMatch(id: String?, lock: String?, ad: MatchUpdateObject): MatchResponseObject =
    updateMatch(id, lock, ad) {
        it should haveSuccessResult
        it.ad shouldNotBe null
        it.ad?.apply {
            if (ad.particapant1 != null)
                particapant1 shouldBe ad.particapant1
            if (ad.particapant2 != null)
                particapant2 shouldBe ad.particapant2
            if (ad.score1 != null)
                score1 shouldBe ad.score1
            if (ad.score1 != null)
                score2 shouldBe ad.score2
            if (ad.matchStatus != null)
                matchStatus shouldBe ad.matchStatus
        }
        it.ad!!
    }

suspend fun <T> Client.updateMatch(id: String?, lock: String?, match: MatchUpdateObject, block: (MatchUpdateResponse) -> T): T =
    withClue("updatedV1: $id, lock: $lock, set: $match") {
        id should beValidId
        lock should beValidLock

        val response = sendAndReceive(
            "match/update", MatchUpdateRequest(
                requestType = "update",
                debug = debug,
                match = match.copy(id = id, lock = lock)
            )
        ) as MatchUpdateResponse

        response.asClue(block)
    }
