package ru.otus.otuskotlin.livescore.common.helpers

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsError
import ru.otus.otuskotlin.livescore.common.models.LsState

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

fun LsContext.fail(error: LsError) {
    addError(error)
    state = LsState.FAILING
}

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: LsError.Level = LsError.Level.ERROR,
) = LsError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)