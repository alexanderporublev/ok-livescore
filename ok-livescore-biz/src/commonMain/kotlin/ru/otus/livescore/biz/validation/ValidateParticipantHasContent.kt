package ru.otus.livescore.biz.validation

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.livescore.cor.ICorChainDsl
import ru.otus.livescore.cor.worker
import ru.otus.otuskotlin.livescore.common.helpers.errorValidation
import ru.otus.otuskotlin.livescore.common.helpers.fail

fun ICorChainDsl<LsContext>.validateParticipant1HasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { matchValidating.participant1.isNotEmpty() && !matchValidating.participant1.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "participant",
                violationCode = "noContent",
                description = "field must contain leters"
            )
        )
    }
}

fun ICorChainDsl<LsContext>.validateParticipant2HasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { matchValidating.participant2.isNotEmpty() && !matchValidating.participant2.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "participan2",
                violationCode = "noContent",
                description = "field must contain leters"
            )
        )
    }
}
