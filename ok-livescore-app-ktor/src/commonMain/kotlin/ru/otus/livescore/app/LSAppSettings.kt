package ru.otus.livescore.app

import ru.otus.LSMatchProcessor

data class LSAppSettings(
    val appUrls: List<String>,
   // val corSettings: MkplCorSettings,
    val processor: LSMatchProcessor,
)
