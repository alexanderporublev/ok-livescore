package ru.otus.livescore.biz.groups

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsCommand
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.chain

fun ICorChainDsl<LsContext>.operation(title: String, command: LsCommand, block: ICorChainDsl<LsContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == LsState.RUNNING }
}
