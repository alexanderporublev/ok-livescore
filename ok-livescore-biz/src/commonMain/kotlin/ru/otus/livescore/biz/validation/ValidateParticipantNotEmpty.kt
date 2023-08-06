package ru.otus.livescore.biz.validation

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.LsState
import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.chain
import ru.otus.livescore.cor.worker
import ru.otus.otuskotlin.livescore.common.helpers.errorValidation
import ru.otus.otuskotlin.livescore.common.helpers.fail


fun ICorChainDsl<LsContext>.validateParticipant1NotEmpty(title: String) = worker {
    this.title = title
    on { matchValidating.participant1.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "participant1",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}

fun ICorChainDsl<LsContext>.validateParticipant2NotEmpty(title: String) = worker {
    this.title = title
    on { matchValidating.participant2.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "participant2",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
