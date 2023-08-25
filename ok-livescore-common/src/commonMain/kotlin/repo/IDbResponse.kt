package ru.otus.otuskotlin.livescore.common.repo

import ru.otus.otuskotlin.livescore.common.models.LsError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<LsError>
}
