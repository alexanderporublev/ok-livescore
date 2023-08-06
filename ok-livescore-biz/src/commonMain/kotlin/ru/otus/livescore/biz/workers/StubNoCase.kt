package ru.otus.livescore.biz.workers

import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.worker
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.helpers.fail
import ru.otus.otuskotlin.livescore.common.models.LsError
import ru.otus.otuskotlin.livescore.common.models.LsState

fun ICorChainDsl<LsContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == LsState.RUNNING }
    handle {
        fail(
            LsError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
