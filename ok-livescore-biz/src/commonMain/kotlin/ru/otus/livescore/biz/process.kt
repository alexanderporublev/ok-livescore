package ru.otus.livescore.biz

import kotlinx.datetime.Clock
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsCommand
import ru.otus.otuskotlin.livescore.common.helpers.asLsError

suspend fun <T> LSMatchProcessor.process(
    command: LsCommand,
    fromTransport : suspend (LsContext) -> Unit,
    sendResponse : suspend (LsContext) -> T,
) {
    val ctx = LsContext(
        timeStart = Clock.System.now(),
    )

    var realCommand = command
    fromTransport(ctx)
    realCommand = ctx.command

    exec(ctx)

    sendResponse(ctx)
}
