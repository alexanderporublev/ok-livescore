package ru.otus.livescore.biz.repo

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.worker

fun ICorChainDsl<LsContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == LsState.RUNNING }
    handle {
        matchRepoPrepare = matchRepoRead.deepCopy().apply {
            this.participant1 = matchValidated.participant1
            this.participant2 = matchValidated.participant2
            this.score1 = matchValidated.score1
            this.score2 = matchValidated.score2
            this.court = matchValidated.court
            this.datetime = matchValidated.datetime
            this.eventId = matchValidated.eventId
            this.status = matchValidated.status
        }
    }
}
