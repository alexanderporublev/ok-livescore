package ru.otus.livescore.backend.repo.sql

open class SqlProperties (
    val url: String = "jdbc:postgresql://localhost:5432/livescore",
    val user: String = "postgres",
    val password: String = "marketplace-pass",
    val schema: String = "marketplace",
    // Delete tables at startup - needed for testing
    val dropDatabase: Boolean = false,
)