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

suspend fun Client.deleteAd(id: String?, lock: String?) {
    withClue("deleteMatchV1: $id, lock: $lock") {
        id should beValidId
        lock should beValidLock

        val response = sendAndReceive(
            "match/delete",
            MatchDeleteRequest(
                requestType = "delete",
                debug = debug,
                match = MatchDeleteObject(id = id, lock = lock)
            )
        ) as MatchDeleteResponse

        response.asClue {
            response should haveSuccessResult
            response.ad shouldNotBe null
            response.ad?.id shouldBe id
        }
    }
}