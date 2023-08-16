package ru.otus.livescore.biz.validation

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.chain

fun ICorChainDsl<LsContext>.validation(block: ICorChainDsl<LsContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == LsState.RUNNING }
}
