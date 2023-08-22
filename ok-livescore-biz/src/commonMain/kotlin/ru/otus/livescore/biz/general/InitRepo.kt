package ru.otus.livescore.biz.general

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.helpers.fail
import ru.otus.otuskotlin.livescore.common.models.LsWorkMode
import ru.otus.otuskotlin.livescore.common.repo.IMatchesRepository
import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.worker
import ru.otus.otuskotlin.livescore.common.helpers.errorAdministration

fun ICorChainDsl<LsContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        matchRepo = when {
            workMode == LsWorkMode.TEST -> settings.repoTest
            workMode == LsWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != LsWorkMode.STUB && matchRepo == IMatchesRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
