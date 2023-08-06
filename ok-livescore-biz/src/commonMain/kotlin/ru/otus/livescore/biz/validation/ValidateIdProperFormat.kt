package ru.otus.livescore.biz.validation

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.chain
import ru.otus.livescore.cor.worker
import ru.otus.otuskotlin.livescore.common.helpers.errorValidation
import ru.otus.otuskotlin.livescore.common.helpers.fail
import ru.otus.otuskotlin.livescore.common.models.LsEventId
import ru.otus.otuskotlin.livescore.common.models.LsMatchId

fun ICorChainDsl<LsContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в MkplAdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { matchValidating.id != LsMatchId.NONE && !matchValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = matchValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}

fun ICorChainDsl<LsContext>.validateEventIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в MkplAdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { matchValidating.eventId != LsEventId.NONE && !matchValidating.eventId.asString().matches(regExp) }
    handle {
        val encodedId = matchValidating.eventId.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "eventid",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}