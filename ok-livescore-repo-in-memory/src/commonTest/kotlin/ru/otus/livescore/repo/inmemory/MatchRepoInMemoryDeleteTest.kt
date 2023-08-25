package ru.otus.livescore.repo.inmemory

import ru.otus.livescore.RepoMatchDeleteTest

class MatchRepoInMemoryDeleteTest : RepoMatchDeleteTest() {
    override val repo = MatchRepoInMemory(
        initObjects = initObjects,
    )
}