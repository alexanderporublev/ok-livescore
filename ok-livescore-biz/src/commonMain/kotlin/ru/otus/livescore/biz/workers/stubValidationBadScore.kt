package ru.otus.livescore.biz.workers

import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.worker
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsError
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.otuskotlin.livescore.common.stubs.LsStubs


fun ICorChainDsl<LsContext>.stubValidationBadScore(title: String) = worker {
    this.title = title
    on { stubCase == LsStubs.BAD_SCORE && state == LsState.RUNNING }
    handle {
        state = LsState.FAILING
        this.errors.add(
            LsError(
                group = "validation",
                code = "validation-score",
                field = "score1",
                message = "Wrong score1 field"
            )
        )
    }
}