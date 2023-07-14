package ru.otus.livescore.blackbox.test

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import ru.otus.api.v1.models.*
import ru.otus.livescore.blackbox.fixture.client.Client
import ru.otus.livescore.blackbox.test.action.v1.*

fun FunSpec.testApiV1(client: Client, prefix: String = "") {
    context("${prefix}v1") {
        test("Create Match ok") {
            client.createMatch()
        }

        test("Read match ok") {
            val created = client.createMatch()
            client.readMatch(created.id).asClue {
                it shouldBe created
            }
        }

        test("Update match ok") {
            val created = client.createMatch()
            client.updateMatch(created.id, created.lock, MatchUpdateObject(score1 = 3))
            client.readMatch(created.id) {
                // TODO раскомментировать, когда будет реальный реп
                //it.ad?.title shouldBe "Selling Nut"
                //it.ad?.description shouldBe someCreateAd.description
            }
        }

        test("Delete match ok") {
            val created = client.createMatch()
            client.deleteAd(created.id, created.lock)
            client.readMatch(created.id) {
                // it should haveError("not-found") TODO раскомментировать, когда будет реальный реп
            }
        }


        test("Offer match ok") {
            val first = client.createMatch(someCreateMatch.copy(particapant1 = "Safronov", score1 = 5))
            val second = client.createMatch(someCreateMatch.copy(particapant2 = "Upatov", score2 = 3))

            withClue("Find offer for supply") {
                client.matches(first.id)
                // TODO раскомментировать, когда будет реальный реп
                // .shouldExistInOrder({it.id == demand.id })
            }

            withClue("Find offer for demand") {
                client.matches(second.id)
                // TODO раскомментировать, когда будет реальный реп
                // .shouldExistInOrder({it.id == supply.id })
            }
        }
    }

}