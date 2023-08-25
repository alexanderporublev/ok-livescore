package ru.otus.livescore.app.plugins

import io.ktor.server.application.*
import ru.otus.livescore.app.LSAppSettings
import ru.otus.livescore.biz.LSMatchProcessor
import ru.otus.otuskotlin.livescore.common.LsCorSettings
import ru.otus.otuskotlin.livescore.logging.common.LsLoggerProvider
import ru.otus.livescore.repo.inmemory.MatchRepoInMemory
import ru.otus.livescore.backend.repo.sql.RepoMatchSQL
import ru.otus.livescore.backend.repo.sql.SqlProperties
import java.time.Duration

//import ru.otus.otuskotlin.livescore.common.MkplCorSettings
//import ru.otus.otuskotlin.marketplace.logging.common.MpLoggerProvider


fun Application.initAppSettings(): LSAppSettings {
    println( environment.config.property("storage.jdbcURL").getString())
    val corSettings = LsCorSettings(
        //loggerProvider = getLoggerProviderConf(),
        repoProd = MatchRepoInMemory(),
        repoTest = RepoMatchSQL(SqlProperties(url = environment.config.property("storage.jdbcURL").getString())),
        repoStub = MatchRepoInMemory ()
    )

    return LSAppSettings(
    appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
//    corSettings = MkplCorSettings(
//        loggerProvider = getLoggerProviderConf(),
    corSettings = corSettings,
    processor = LSMatchProcessor(corSettings),
    )
}

//expect fun Application.getLoggerProviderConf(): MpLoggerProvider
