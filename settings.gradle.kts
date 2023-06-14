rootProject.name = "ok-livescore"


pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val ktorVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("io.ktor.plugin") version ktorVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
    }
}




include("ok-livescore")
include("ok-livescore-api-v1")
include("ok-livescore-common")
include("ok-livescore-mappers-v1")
include("ok-livescore-app-ktor")
include("ok-livescore-biz")
include("ok-livescore-stubs")
include("ok-livescore-app-ktor:commonTest")
findProject(":ok-livescore-app-ktor:commonTest")?.name = "commonTest"
