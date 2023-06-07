package ru.otus.livescore.stubs

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.livescore.common.NONE
import ru.otus.otuskotlin.livescore.common.models.*

object LsMatchStubSample {
    val LS_MATCH1: LsMatch
        get() = LsMatch(
            id = LsMatchId("666"),
            eventId = LsEventId("777"),
            participant1 = "Ivanov",
            participant2 = "Petrov",
            score1 = 0,
            score2 = 0,
            court = "1",
            datetime = Instant.NONE,
            status = LsMatchStatus.INPROGRESS
        )
}
