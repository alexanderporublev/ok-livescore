package ru.otus.otuskotlin.livescore.common.repo

import ru.otus.otuskotlin.livescore.common.models.LsMatch

data class DbMatchRequest(
    val match: LsMatch
)
