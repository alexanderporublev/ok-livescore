package ru.otus.otuskotlin.livescore.common.repo

import ru.otus.otuskotlin.livescore.common.helpers.errorEmptyId as lsErrorEmptyId
import ru.otus.otuskotlin.livescore.common.helpers.errorNotFound as lsErrorNotFound
import ru.otus.otuskotlin.livescore.common.helpers.errorRepoConcurrency
import ru.otus.otuskotlin.livescore.common.models.LsMatch
import ru.otus.otuskotlin.livescore.common.models.LsMatchLock
import ru.otus.otuskotlin.livescore.common.models.LsError

data class DbMatchResponse(
    override val data: LsMatch?,
    override val isSuccess: Boolean,
    override val errors: List<LsError> = emptyList()
): IDbResponse<LsMatch> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbMatchResponse(null, true)
        fun success(result: LsMatch) = DbMatchResponse(result, true)
        fun error(errors: List<LsError>, data: LsMatch? = null) = DbMatchResponse(data, false, errors)
        fun error(error: LsError, data: LsMatch? = null) = DbMatchResponse(data, false, listOf(error))

        val errorEmptyId = error(lsErrorEmptyId)

        fun errorConcurrent(lock: LsMatchLock, ad: LsMatch?) = error(
            errorRepoConcurrency(lock, ad?.lock?.let { LsMatchLock(it.asString()) }),
            ad
        )

        val errorNotFound = error(lsErrorNotFound)
    }
}
