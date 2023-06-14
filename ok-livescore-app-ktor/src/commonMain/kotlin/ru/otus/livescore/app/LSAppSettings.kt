package ru.otus.livescore.app

import ru.otus.LSMatchProcessor
//import ru.otus.otuskotlin.marketplace.common.MkplCorSettings

data class LSAppSettings(
    val appUrls: List<String>,
   // val corSettings: MkplCorSettings,
    val processor: LSMatchProcessor,
)
