package ru.otus.livescore.biz.repo

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.otuskotlin.livescore.common.repo.DbMatchRequest
import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.worker

fun ICorChainDsl<LsContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление объявления в БД"
    on { state == LsState.RUNNING }
    handle {
        val request = DbMatchRequest(matchRepoPrepare)
        val result = matchRepo.createMatch(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            matchRepoDone = resultAd
        } else {
            state = LsState.FAILING
            errors.addAll(result.errors)
        }
    }
}
