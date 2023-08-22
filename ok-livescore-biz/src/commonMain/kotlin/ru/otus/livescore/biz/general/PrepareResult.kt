package ru.otus.livescore.biz.general

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.otuskotlin.livescore.common.models.LsWorkMode
import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.worker

fun ICorChainDsl<LsContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != LsWorkMode.STUB }
    handle {
        matchResponse = matchRepoDone
        matchesResponse = matchesRepoDone
        state = when (val st = state) {
            LsState.RUNNING -> LsState.FINISHING
            else -> st
        }
    }
}
