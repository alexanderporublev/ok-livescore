rootProject.name = "ok-livescore"


pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
    }
}




include("ok-livescore")
include("ok-livescore-api-v1")
include("ok-livescore-common")
include("ok-livescore-mappers-v1")