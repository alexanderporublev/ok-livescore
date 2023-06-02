package ru.otus.otuskotlin.livescore.common.helpers

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsCommand

fun LsContext.isUpdatableCommand() =
    this.command in listOf(LsCommand.CREATE, LsCommand.UPDATE, LsCommand.DELETE)
