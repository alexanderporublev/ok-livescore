plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":ok-livescore-api-v1"))
    implementation(project(":ok-livescore-common"))

    testImplementation(kotlin("test-junit"))
}