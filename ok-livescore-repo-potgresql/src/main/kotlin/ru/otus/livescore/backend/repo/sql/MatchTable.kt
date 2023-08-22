package ru.otus.livescore.backend.repo.sql
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otus.otuskotlin.livescore.common.models.*

object MatchTable : Table("match") {
    val id = varchar("id", 128)
    val participant1 = varchar("participant1", 128)
    val participant2 = varchar("participant2", 128)
    val score1  = integer("score1")
    val score2  = integer("score2")
    val court = varchar("court", 512)
    val eventId = varchar("eventid", 128)
    val matchStatus = enumeration("matchstatus", LsMatchStatus::class)
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)
    fun from(res: InsertStatement<Number>) = LsMatch(
        id = LsMatchId(res[id].toString()),
        participant1 = res[participant1],
        participant2 = res[participant2],
        score1 = res[score1],
        score2 = res[score2],
        eventId = LsEventId(res[eventId]),
        court = res[court],
        status = res[matchStatus],
        lock = LsMatchLock(res[lock])
    )

    fun from(res: ResultRow) = LsMatch(
        id = LsMatchId(res[id].toString()),
        participant1 = res[participant1],
        participant2 = res[participant2],
        score1 = res[score1],
        score2 = res[score2],
        eventId = LsEventId(res[eventId]),
        court = res[court],
        status = res[matchStatus],
        lock = LsMatchLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, match: LsMatch, randomUuid: () -> String) {
        it[id] = match.id.takeIf { it != LsMatchId.NONE }?.asString() ?: randomUuid()
        it[participant1] = match.participant1
        it[participant2] = match.participant2
        it[score1] = match.score1
        it[score2] = match.score2
        it[eventId] = match.eventId.asString()
        it[court] = match.court
        it[matchStatus] = match.status
        it[lock] = match.lock.takeIf { it != LsMatchLock.NONE }?.asString() ?: randomUuid()
    }
}