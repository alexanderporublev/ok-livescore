package ru.otus.otuskotlin.livescore.common.models
import ru.otus.otuskotlin.livescore.common.NONE

import kotlinx.datetime.Instant

data class LsMatch(
    var id: LsMatchId = LsMatchId.NONE,
    var eventId : LsEventId = LsEventId.NONE,
    var participant1: String = "",
    var participant2: String = "",
    var score1: Int = 0,
    var score2: Int = 0,
    var court: String = "",
    var datetime: Instant = Instant.NONE,
    var status: LsMatchStatus = LsMatchStatus.NONE,
    var lock: LsMatchLock = LsMatchLock.NONE,
){

    fun deepCopy(): LsMatch = copy(

    )
}
