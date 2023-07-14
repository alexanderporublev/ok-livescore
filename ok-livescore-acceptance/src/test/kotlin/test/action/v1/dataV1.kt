package ru.otus.livescore.blackbox.test.action.v1

import ru.otus.api.v1.models.*

val debug = MatchDebug(mode = MatchRequestDebugMode.STUB, stub = MatchRequestDebugStubs.SUCCESS)

val someCreateMatch = MatchCreateObject(
    particapant1 = "Ivanov",
    particapant2 = "Petrov",
    matchStatus = MatchStatus.INPROGRESS,
)
