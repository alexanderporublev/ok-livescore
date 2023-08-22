package ru.otus.otuskotlin.livescore.common.repo

interface IMatchesRepository {
    suspend fun createMatch(rq: DbMatchRequest): DbMatchResponse
    suspend fun readMatch(rq: DbMatchIdRequest): DbMatchResponse
    suspend fun updateMatch(rq: DbMatchRequest): DbMatchResponse
    suspend fun deleteMatch(rq: DbMatchIdRequest): DbMatchResponse

    companion object {
        val NONE = object : IMatchesRepository {
            override suspend fun createMatch(rq: DbMatchRequest): DbMatchResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readMatch(rq: DbMatchIdRequest): DbMatchResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateMatch(rq: DbMatchRequest): DbMatchResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteMatch(rq: DbMatchIdRequest): DbMatchResponse {
                TODO("Not yet implemented")
            }

        }
    }
}
