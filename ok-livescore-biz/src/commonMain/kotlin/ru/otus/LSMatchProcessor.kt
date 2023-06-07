package ru.otus

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsWorkMode
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.otuskotlin.livescore.common.models.LsCommand
import ru.otus.livescore.stubs.LsMatchStub

class LSMatchProcessor {
    suspend fun exec(ctx: LsContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == LsWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        ctx.state = LsState.RUNNING
        when (ctx.command) {

            LsCommand.MATCHES -> {
                ctx.matchesResponse.addAll(LsMatchStub.prepareMatchesList())
            }
            else -> {
                ctx.matchResponse = LsMatchStub.get()
            }
        }
    }

}