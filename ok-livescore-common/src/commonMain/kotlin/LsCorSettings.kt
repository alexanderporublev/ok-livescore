package ru.otus.otuskotlin.livescore.common

import ru.otus.otuskotlin.livescore.logging.common.LsLoggerProvider
import ru.otus.otuskotlin.livescore.common.repo.IMatchesRepository

class LsCorSettings (
    val loggerProvider: LsLoggerProvider = LsLoggerProvider(),
    val repoStub: IMatchesRepository = IMatchesRepository.NONE,
    val repoTest: IMatchesRepository = IMatchesRepository.NONE,
    val repoProd: IMatchesRepository = IMatchesRepository.NONE,
    ){
    companion object {
        val NONE = LsCorSettings()
    }
}