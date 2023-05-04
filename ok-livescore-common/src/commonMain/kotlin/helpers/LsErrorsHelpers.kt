package ru.otus.otuskotlin.livescore.common.helpers

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsError

fun Throwable.asLsError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = LsError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun LsContext.addError(vararg error: LsError) = errors.addAll(error)
