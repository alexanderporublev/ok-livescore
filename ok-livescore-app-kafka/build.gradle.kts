plugins {
    application
    kotlin("jvm")
    id("com.bmuschko.docker-java-application")
}

application {
    mainClass.set("ru.otus.livescore.app.kafka.MainKt")
}

docker {
    javaApplication {
        mainClassName.set(application.mainClass.get())
        baseImage.set("openjdk:17")
        maintainer.set("(c) Otus")
//        ports.set(listOf(8080))
        val imageName = project.name
        images.set(
            listOf(
                "$imageName:${project.version}",
                "$imageName:latest"
            )
        )
        jvmArgs.set(listOf("-Xms256m", "-Xmx512m"))
    }
}

dependencies {
    val kafkaVersion: String by project
    val coroutinesVersion: String by project
    val atomicfuVersion: String by project
    val logbackVersion: String by project
    val kotlinLoggingJvmVersion: String by project

    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:atomicfu:$atomicfuVersion")

    // log
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")
    //implementation(project(":ok-marketplace-mappers-log1"))

    // transport models
    implementation(project(":ok-livescore-common"))
    implementation(project(":ok-livescore-api-v1"))
    implementation(project(":ok-livescore-mappers-v1"))
    implementation(project(":ok-livescore-lib-logging-common"))

    // logic
    implementation(project(":ok-livescore-biz"))
    // other
    //implementation(project(":ok-livescore-lib-logging-logback"))

    testImplementation(kotlin("test-junit"))
}
