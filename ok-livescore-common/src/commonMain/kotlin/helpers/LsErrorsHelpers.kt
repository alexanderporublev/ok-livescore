package ru.otus.otuskotlin.livescore.common.helpers

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsError
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.otuskotlin.livescore.common.models.LsMatchLock
import ru.otus.otuskotlin.livescore.common.exceptions.RepoConcurrencyException

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

fun errorRepoConcurrency(
    expectedLock: LsMatchLock,
    actualLock: LsMatchLock?,
    exception: Exception? = null,
) = LsError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)

val errorNotFound = LsError(
    field = "id",
    message = "Not Found",
    code = "not-found"
)

val errorEmptyId = LsError(
    field = "id",
    message = "Id must not be null or blank"
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    exception: Exception? = null,
    level: LsError.Level = LsError.Level.ERROR,
) = LsError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
    exception = exception,
)