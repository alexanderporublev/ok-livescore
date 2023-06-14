package ru.otus.otuskotlin.marketplace.mappers.v1

import ru.otus.api.v1.models.*
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.*
import ru.otus.otuskotlin.livescore.common.stubs.LsStubs
import ru.otus.otuskotlin.livescore.mappers.v1.exceptions.UnknownRequestClass
import ru.otus.otuskotlin.livescore.common.NONE
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.util.Date

fun LsContext.fromTransport(request: IRequest) = when (request) {
    is MatchCreateRequest -> fromTransport(request)
    is MatchReadRequest -> fromTransport(request)
    is MatchUpdateRequest -> fromTransport(request)
    is MatchDeleteRequest -> fromTransport(request)
    is MatchesRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)


}

private fun String?.toMatchId() = this?.let { LsMatchId(it) } ?: LsMatchId.NONE
private fun String?.toMatchWithId() = LsMatch(id = this.toMatchId())
private fun IRequest?.requestId() = this?.requestId?.let { LsRequestId(it) } ?: LsRequestId.NONE

private fun MatchDebug?.transportToWorkMode(): LsWorkMode = when (this?.mode) {
    MatchRequestDebugMode.PROD -> LsWorkMode.PROD
    MatchRequestDebugMode.TEST -> LsWorkMode.TEST
    MatchRequestDebugMode.STUB -> LsWorkMode.STUB
    null -> LsWorkMode.PROD
}

private fun MatchDebug?.transportToStubCase(): LsStubs = when (this?.stub) {
    MatchRequestDebugStubs.SUCCESS -> LsStubs.SUCCESS
    MatchRequestDebugStubs.NOT_FOUND -> LsStubs.NOT_FOUND
    MatchRequestDebugStubs.BAD_ID -> LsStubs.BAD_ID
    MatchRequestDebugStubs.BAD_PARTICIPANT_NAME-> LsStubs.BAD_PARTICIPANT_NAME
    MatchRequestDebugStubs.BAD_SCORE -> LsStubs.BAD_SCORE
    MatchRequestDebugStubs.CANNOT_DELETE -> LsStubs.CANNOT_DELETE
    MatchRequestDebugStubs.BAD_SEARCH_STRING -> LsStubs.BAD_SEARCH_STRING
    null -> LsStubs.NONE
}

fun LsContext.fromTransport(request: MatchCreateRequest) {
    command = LsCommand.CREATE
    requestId = request.requestId()
    matchRequest = request.match?.toInternal() ?: LsMatch()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun LsContext.fromTransport(request: MatchReadRequest) {
    command = LsCommand.READ
    requestId = request.requestId()
    matchRequest = request.match?.id.toMatchWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun LsContext.fromTransport(request: MatchUpdateRequest) {
    command = LsCommand.UPDATE
    requestId = request.requestId()
    matchRequest = request.match?.toInternal() ?: LsMatch()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun LsContext.fromTransport(request: MatchDeleteRequest) {
    command = LsCommand.DELETE
    requestId = request.requestId()
    matchRequest = request.match?.id.toMatchWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}


fun LsContext.fromTransport(request: MatchesRequest) {
    command = LsCommand.MATCHES
    requestId = request.requestId()
    matchRequest = request.ad?.id.toMatchWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

//private fun AdSearchFilter?.toInternal(): MkplAdFilter = MkplAdFilter(
//    searchString = this?.searchString ?: ""
//)

private fun MatchCreateObject.toInternal(): LsMatch = LsMatch(
    eventId =  LsEventId(this.eventId?: ""),
    participant1 = this.particapant1 ?: "",
    participant2 = this.particapant2 ?: "",
    score1 = this.score1 ?: 0,
    score2 = this.score2 ?: 0,
    court = this.court ?: "",
    datetime = LocalDateTime.parse( this.datetime ?: "").toInstant(TimeZone.UTC), //взять TimeZone из строки
    status = this.matchStatus.fromTransport()
)

private fun MatchUpdateObject.toInternal(): LsMatch = LsMatch(
    id = this.id.toMatchId(),
    participant1 = this.particapant1 ?: "",
    participant2 = this.particapant2 ?: "",
    score1 = this.score1 ?: 0,
    score2 = this.score2 ?: 0,
    court = this.court ?: "",
    datetime = LocalDateTime.parse( this.datetime ?: "").toInstant(TimeZone.UTC), //взять TimeZone из строки
    status = this.matchStatus.fromTransport()
)

private fun MatchStatus?.fromTransport(): LsMatchStatus = when (this) {
    MatchStatus.INPROGRESS -> LsMatchStatus.INPROGRESS
    MatchStatus.COMPLETED -> LsMatchStatus.FINISHED
    null ->  LsMatchStatus.NONE
}
//
//private fun DealSide?.fromTransport(): MkplDealSide = when (this) {
//    DealSide.DEMAND -> MkplDealSide.DEMAND
//    DealSide.SUPPLY -> MkplDealSide.SUPPLY
//    null -> MkplDealSide.NONE
//}
//
