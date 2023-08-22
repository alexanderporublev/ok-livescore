package ru.otus.otuskotlin.livescore.common.exceptions

import ru.otus.otuskotlin.livescore.common.models.LsMatchLock

class RepoConcurrencyException(expectedLock: LsMatchLock, actualLock: LsMatchLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
