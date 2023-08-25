package ru.otus.otuskotlin.livescore.common.repo

import ru.otus.otuskotlin.livescore.common.models.LsMatch
import ru.otus.otuskotlin.livescore.common.models.LsMatchId
import ru.otus.otuskotlin.livescore.common.models.LsMatchLock

data class DbMatchIdRequest(
    val id: LsMatchId,
    val lock: LsMatchLock = LsMatchLock.NONE,
) {
    constructor(match: LsMatch): this(match.id, match.lock)
}
