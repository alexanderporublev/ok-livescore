package ru.otus.livescore.repo.inmemory

import ru.otus.livescore.RepoMatchUpdateTest

class MatchRepoInMemoryUpdateTest : RepoMatchUpdateTest() {
    override val repo = MatchRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}