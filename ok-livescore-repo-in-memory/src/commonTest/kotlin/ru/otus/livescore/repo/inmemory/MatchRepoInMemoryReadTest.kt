package ru.otus.livescore.repo.inmemory

import ru.otus.livescore.RepoMatchUpdateTest

class MatchRepoInMemoryReadTest  : RepoMatchUpdateTest(){
    override val repo = MatchRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}