package ru.otus.livescore.biz.validation

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.chain
import ru.otus.livescore.cor.worker
import ru.otus.otuskotlin.livescore.common.helpers.errorValidation
import ru.otus.otuskotlin.livescore.common.helpers.fail

fun ICorChainDsl<LsContext>.validateIdNotEmpty(title: String) = worker {
    this.title = title
    on { matchValidating.id.asString().isEmpty()  }
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }

}


fun ICorChainDsl<LsContext>.validateEventIdNotEmpty(title: String) = worker {
    this.title = title
    on { matchValidating.eventId.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "eventid",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
