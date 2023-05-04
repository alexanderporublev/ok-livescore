package ru.otus.otuskotlin.livescore.common.models

data class LsError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
