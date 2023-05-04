package ru.otus.otuskotlin.livescore.common.models

data class LsMatchFilter(
    var searchString: String = "",
    var eventId: LsEventId = LsEventId.NONE,
)
