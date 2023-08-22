package ru.otus.livescore.repo.inmemory.model
import ru.otus.otuskotlin.livescore.common.models.*

data class MatchEntity (
    val id: String? = null,
    val participant1: String? = null,
    val participant2: String? = null,
    val score1: String? = null,
    val score2: String? = null,
    val eventId: String? = null,
    val court: String? = null,
    val status: String? = null,
    val lock: String? = null,
){
    constructor(model: LsMatch) : this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        participant1 = model.participant1.takeIf { it.isNotBlank() },
        participant2 = model.participant2.takeIf { it.isNotBlank() },
        score1 = model.score1.takeIf { it > 0 }.toString(),
        score2 = model.score2.takeIf { it > 0 }.toString(),
        eventId = model.eventId.asString().takeIf { it.isNotBlank() },
        court = model.court.takeIf { it.isNotBlank() },
        status = model.status.takeIf { it != LsMatchStatus.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = LsMatch(
        id = id?.let { LsMatchId(it) }?: LsMatchId.NONE,
        participant1 = participant1?: "",
        participant2 = participant2?: "",
        score1 = score1?.toInt()?: -1,
        score2 = score2?.toInt()?: -1,
        eventId = eventId?.let { LsEventId(it) } ?: LsEventId.NONE,
        court = court?: "",
        status = status?.let { LsMatchStatus.valueOf(it) }?: LsMatchStatus.NONE,
        lock = lock?.let { LsMatchLock(it) }?: LsMatchLock.NONE,
    )
}