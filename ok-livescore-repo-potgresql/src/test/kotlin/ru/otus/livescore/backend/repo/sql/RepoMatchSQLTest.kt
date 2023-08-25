package ru.otus.livescore.backend.repo.sql

import ru.otus.livescore.RepoMatchCreateTest
import ru.otus.livescore.RepoMatchDeleteTest
import ru.otus.livescore.RepoMatchReadTest
import ru.otus.livescore.RepoMatchUpdateTest
import ru.otus.otuskotlin.livescore.common.repo.IMatchesRepository

class RepoMatchSQLCreateTest : RepoMatchCreateTest() {
    override val repo: IMatchesRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoMatchSQLDeleteTest : RepoMatchDeleteTest() {
    override val repo: IMatchesRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoMatchSQLReadTest : RepoMatchReadTest() {
    override val repo: IMatchesRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}


class RepoMatchSQLUpdateTest : RepoMatchUpdateTest() {
    override val repo: IMatchesRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}
