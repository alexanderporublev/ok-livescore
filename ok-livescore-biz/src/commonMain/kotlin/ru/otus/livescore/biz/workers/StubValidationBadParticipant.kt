package ru.otus.livescore.biz.workers

import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.worker
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsError
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.otuskotlin.livescore.common.stubs.LsStubs


fun ICorChainDsl<LsContext>.stubValidationBadParticipant(title: String) = worker {
    this.title = title
    on { stubCase == LsStubs.BAD_PARTICIPANT_NAME && state == LsState.RUNNING }
    handle {
        state = LsState.FAILING
        this.errors.add(
            LsError(
                group = "validation",
                code = "validation-participant",
                field = "participant1",
                message = "Wrong participant1 field"
            )
        )
    }
}