rootProject.name = "ok-livescore"

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
    }
}



include("ok-livescore")


