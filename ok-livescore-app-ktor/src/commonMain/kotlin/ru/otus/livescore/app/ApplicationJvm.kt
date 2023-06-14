package ru.otus.livescore.app

import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.slf4j.event.Level
import ru.otus.livescore.app.module
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import ru.otus.livescore.app.plugins.initAppSettings
import ru.otus.otuskotlin.livescore.app.v1.v1Match
import java.time.Duration
import ru.otus.livescore.app.module as commonModule

// function with config (application.conf)
fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

private val clazz = Application::moduleJvm::class.qualifiedName ?: "Application"

@Suppress("unused") // Referenced in application.conf
fun Application.moduleJvm(appSettings: LSAppSettings = initAppSettings()) {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    commonModule(appSettings, false)

    install(CachingHeaders)
    install(DefaultHeaders)
    install(AutoHeadResponse)

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowCredentials = true
        anyHost() // TODO remove
    }



    @Suppress("OPT_IN_USAGE")
    install(Locations)

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        route("v1") {
            install(ContentNegotiation) {
                jackson {
                    setConfig(apiV1Mapper.serializationConfig)
                    setConfig(apiV1Mapper.deserializationConfig)
                }
            }

            v1Match(appSettings)
        }


        static("static") {
            resources("static")
        }
    }
}

