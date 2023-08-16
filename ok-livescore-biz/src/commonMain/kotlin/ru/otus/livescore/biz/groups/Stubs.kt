package ru.otus.livescore.biz.groups

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.otuskotlin.livescore.common.models.LsWorkMode
import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.chain

fun ICorChainDsl<LsContext>.stubs(title: String, block: ICorChainDsl<LsContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == LsWorkMode.STUB && state == LsState.RUNNING }
}
