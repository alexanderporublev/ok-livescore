rootProject.name = "ok-livescore"


pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val ktorVersion: String by settings
    val bmuschkoVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("io.ktor.plugin") version ktorVersion apply false
        id("org.openapi.generator") version openapiVersion apply false

        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-spring-boot-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false

    }
}




include("ok-livescore")
include("ok-livescore-acceptance")
include("ok-livescore-api-v1")
include("ok-livescore-common")
include("ok-livescore-mappers-v1")
include("ok-livescore-app-ktor")
include("ok-livescore-lib-cor")
include("ok-livescore-biz")
include("ok-livescore-stubs")
include("ok-livescore-app-kafka")
include("ok-livescore-lib-logging-common")
include("ok-livescore-repo-in-memory")
include("ok-livescore-repo-tests")
include("ok-livescore-repo-potgresql")
