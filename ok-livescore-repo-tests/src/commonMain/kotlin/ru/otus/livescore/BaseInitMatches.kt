package ru.otus.livescore

import ru.otus.otuskotlin.livescore.common.models.*

abstract class BaseInitMatches(val op: String): IInitObjects<LsMatch> {

    open val lockOld: LsMatchLock = LsMatchLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: LsMatchLock = LsMatchLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        lock: LsMatchLock = lockOld,
    ) = LsMatch(
        id = LsMatchId("ad-repo-$op-$suf"),
        participant1 = "Ivanov",
        participant2 = "Petrov",
        score1 = 0,
        score2 = 0,
        court = "1",
        eventId = LsEventId("999"),
        status = LsMatchStatus.INPROGRESS,
        lock = lock,
    )
}
