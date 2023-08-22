package ru.otus.otuskotlin.livescore.common.repo

import ru.otus.otuskotlin.livescore.common.models.LsMatch
import ru.otus.otuskotlin.livescore.common.models.LsError

data class DbMatchesResponse(
    override val data: List<LsMatch>?,
    override val isSuccess: Boolean,
    override val errors: List<LsError> = emptyList(),
): IDbResponse<List<LsMatch>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbMatchesResponse(emptyList(), true)
        fun success(result: List<LsMatch>) = DbMatchesResponse(result, true)
        fun error(errors: List<LsError>) = DbMatchesResponse(null, false, errors)
        fun error(error: LsError) = DbMatchesResponse(null, false, listOf(error))
    }
}
