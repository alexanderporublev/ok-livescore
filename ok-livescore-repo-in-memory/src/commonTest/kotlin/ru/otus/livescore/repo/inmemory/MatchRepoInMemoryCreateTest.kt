package ru.otus.livescore.repo.inmemory
import ru.otus.livescore.RepoMatchCreateTest
class MatchRepoInMemoryCreateTest : RepoMatchCreateTest() {
    override val repo = MatchRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}