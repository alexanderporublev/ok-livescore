package ru.otus.livescore.biz.workers

import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.worker
import ru.otus.livescore.stubs.LsMatchStub
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsMatchStatus
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.otuskotlin.livescore.common.stubs.LsStubs


fun ICorChainDsl<LsContext>.stubCreateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == LsStubs.SUCCESS && state == LsState.RUNNING }
    handle {
        state = LsState.FINISHING
        val stub = LsMatchStub.prepareResult {
            matchRequest.participant1.takeIf { it.isNotBlank() }?.also { this.participant1 = it }
            matchRequest.participant2.takeIf { it.isNotBlank() }?.also { this.participant2 = it }
            matchRequest.score1.takeIf { it >= 0 }?.also { this.score1 = it }
            matchRequest.score2.takeIf { it >= 0 }?.also { this.score2 = it }
            matchRequest.eventId.takeIf { it.asString().isNotBlank() }?.also { this.eventId= it }
            matchRequest.court.takeIf { it.isNotBlank() }?.also { this.court = it }
            matchRequest.status.takeIf { it != LsMatchStatus.NONE  }.also { this.status = matchRequest.status }
        }
        matchResponse = stub
    }
}