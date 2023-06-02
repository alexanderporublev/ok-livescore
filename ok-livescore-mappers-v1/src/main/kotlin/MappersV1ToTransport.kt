package ru.otus.otuskotlin.marketplace.mappers.v1

import ru.otus.api.v1.models.*
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.*
import ru.otus.otuskotlin.livescore.mappers.v1.exceptions.UnknownLsCommand

fun LsContext.toTransportMatch(): IResponse = when (val cmd = command) {
    LsCommand.CREATE -> toTransportCreate()
    LsCommand.READ -> toTransportRead()
    LsCommand.UPDATE -> toTransportUpdate()
    LsCommand.DELETE -> toTransportDelete()
    LsCommand.MATCHES -> toTransportMatches()
    LsCommand.NONE -> throw UnknownLsCommand(cmd)
}

fun LsContext.toTransportCreate() = MatchCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == LsState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = matchResponse.toTransportMatch()
)

fun LsContext.toTransportRead() = MatchReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == LsState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = matchResponse.toTransportMatch()
)

fun LsContext.toTransportUpdate() = MatchUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == LsState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = matchResponse.toTransportMatch()
)

fun LsContext.toTransportDelete() = MatchDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == LsState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = matchResponse.toTransportMatch()
)



fun LsContext.toTransportMatches() = MatchesResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == LsState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ads = matchesResponse.toTransportMatches()
)

fun List<LsMatch>.toTransportMatches(): List<MatchResponseObject>? = this
    .map { it.toTransportMatch() }
    .toList()
    .takeIf { it.isNotEmpty() }

//fun LsContext.toTransportInit() = MatchInitResponse(
//    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
//    result = if (errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
//    errors = errors.toTransportErrors(),
//)

private fun LsMatch.toTransportMatch(): MatchResponseObject = MatchResponseObject(
    id = id.takeIf { it != LsMatchId.NONE }?.asString(),
    eventId = eventId.takeIf { it != LsEventId.NONE }?.asString(),
    particapant1 = participant1.takeIf { it.isNotBlank() },
    particapant2 = participant2.takeIf { it.isNotBlank() },
    score1 = score1,
    score2 = score2,
    court = court.takeIf { it.isNotBlank() },
    datetime = datetime.toString(),
    matchStatus = status.toTransportMatchStatus()
)



private fun LsMatchStatus.toTransportMatchStatus(): MatchStatus? = when (this) {
    LsMatchStatus.FINISHED -> MatchStatus.COMPLETED
    LsMatchStatus.INPROGRESS -> MatchStatus.INPROGRESS
    LsMatchStatus.NONE -> null
}

private fun List<LsError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportError() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun LsError.toTransportError() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
