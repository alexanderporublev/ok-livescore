plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {}
    linuxX64 {}
    macosX64 {}
    macosArm64 {}

    sourceSets {
        val coroutinesVersion: String by project
        val kotlinLoggingJvmVersion: String by project

        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":ok-livescore-common"))
                implementation(project(":ok-livescore-stubs"))
                implementation(project(":ok-livescore-lib-cor"))
                implementation(project(":ok-livescore-lib-logging-common"))

            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")

            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
