package ru.otus.livescore.biz.repo

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.worker

fun ICorChainDsl<LsContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Готовим данные к удалению из БД
    """.trimIndent()
    on { state == LsState.RUNNING }
    handle {
        matchRepoPrepare = matchValidated.deepCopy()
    }
}
