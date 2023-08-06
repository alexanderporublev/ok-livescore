package ru.otus.otuskotlin.livescore.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.livescore.common.models.*
import ru.otus.otuskotlin.livescore.common.stubs.LsStubs
import ru.otus.otuskotlin.livescore.common.models.LsState

data class LsContext(
    var command: LsCommand = LsCommand.NONE,
    var state: LsState = LsState.NONE,
    val errors: MutableList<LsError> = mutableListOf(),
    var settings: LsCorSettings = LsCorSettings.NONE,

    var workMode: LsWorkMode = LsWorkMode.PROD,
    var stubCase: LsStubs = LsStubs.NONE,

    var requestId: LsRequestId = LsRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var matchRequest: LsMatch = LsMatch(),
    var matchFilterRequest: LsMatchFilter = LsMatchFilter(),
    var matchResponse: LsMatch = LsMatch(),
    var matchesResponse: MutableList<LsMatch> = mutableListOf(),

    var matchValidating: LsMatch = LsMatch(),

    var matchValidated: LsMatch = LsMatch(),


    )
