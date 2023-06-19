package ru.otus.livescore.app.plugins

import io.ktor.server.application.*
import ru.otus.livescore.app.LSAppSettings
import ru.otus.LSMatchProcessor
//import ru.otus.otuskotlin.livescore.common.MkplCorSettings
//import ru.otus.otuskotlin.marketplace.logging.common.MpLoggerProvider

fun Application.initAppSettings(): LSAppSettings = LSAppSettings(
    appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
//    corSettings = MkplCorSettings(
//        loggerProvider = getLoggerProviderConf(),
//    ),
    processor = LSMatchProcessor(),
)

//expect fun Application.getLoggerProviderConf(): MpLoggerProvider
