package ru.otus.livescore.biz.workers

import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.worker
import ru.otus.livescore.stubs.LsMatchStub
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsMatchStatus
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.otuskotlin.livescore.common.stubs.LsStubs

fun ICorChainDsl<LsContext>.stubReadSuccess(title: String) = worker {
    this.title = title
    on { stubCase == LsStubs.SUCCESS && state == LsState.RUNNING }
    handle {
        state = LsState.FINISHING
        val stub = LsMatchStub.prepareResult {
        }
        matchResponse = stub
    }
}