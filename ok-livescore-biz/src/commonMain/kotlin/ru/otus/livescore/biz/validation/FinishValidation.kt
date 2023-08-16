package ru.otus.livescore.biz.validation

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.worker

fun ICorChainDsl<LsContext>.finishMatchValidation(title: String) = worker {
    this.title = title
    on { state == LsState.RUNNING }
    handle {
        matchValidated = matchValidating
    }
}

