package ru.otus.livescore.app

import ru.otus.livescore.biz.LSMatchProcessor
import ru.otus.otuskotlin.livescore.common.LsCorSettings

data class LSAppSettings(
    val appUrls: List<String>,
    val corSettings: LsCorSettings,
    val processor: LSMatchProcessor,
)
