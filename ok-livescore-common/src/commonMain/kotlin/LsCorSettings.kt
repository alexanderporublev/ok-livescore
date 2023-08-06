package ru.otus.otuskotlin.livescore.common

import ru.otus.otuskotlin.livescore.logging.common.LsLoggerProvider

class LsCorSettings (
    val loggerProvider: LsLoggerProvider = LsLoggerProvider(),
    ){
    companion object {
        val NONE = LsCorSettings()
    }
}