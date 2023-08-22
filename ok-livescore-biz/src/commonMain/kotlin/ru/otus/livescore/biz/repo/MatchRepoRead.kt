package ru.otus.livescore.biz.repo

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.otuskotlin.livescore.common.repo.DbMatchIdRequest
import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.worker

fun ICorChainDsl<LsContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == LsState.RUNNING }
    handle {
        val request = DbMatchIdRequest(matchValidated)
        val result = matchRepo.readMatch(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            matchRepoRead = resultAd
        } else {
            state = LsState.FAILING
            errors.addAll(result.errors)
        }
    }
}
