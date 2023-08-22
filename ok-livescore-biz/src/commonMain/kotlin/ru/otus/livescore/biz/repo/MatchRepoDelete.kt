package ru.otus.livescore.biz.repo

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.otuskotlin.livescore.common.repo.DbMatchIdRequest
import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.worker
fun ICorChainDsl<LsContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД по ID"
    on { state == LsState.RUNNING }
    handle {
        val request = DbMatchIdRequest(matchRepoPrepare)
        val result = matchRepo.deleteMatch(request)
        if (!result.isSuccess) {
            state = LsState.FAILING
            errors.addAll(result.errors)
        }
        matchRepoDone = matchRepoRead
    }
}
