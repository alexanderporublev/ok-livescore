package ru.otus.livescore.biz.workers

import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.worker
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsState

fun ICorChainDsl<LsContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == LsState.NONE }
    handle { state = LsState.RUNNING }
}
