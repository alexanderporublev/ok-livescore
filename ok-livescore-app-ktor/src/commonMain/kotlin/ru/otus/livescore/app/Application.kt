package ru.otus.livescore.app

import io.ktor.serialization.jackson.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import ru.otus.livescore.app.plugins.initAppSettings
import ru.otus.otuskotlin.livescore.app.v1.v1Match
//import ru.otus.otuskotlin.marketplace.app.v2.wsHandlerV2

fun Application.module(appSettings: LSAppSettings = initAppSettings(), installPlugins: Boolean = true) {
    if (installPlugins) {
        install(WebSockets)
    }

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

//        webSocket("/ws/v1") {
//            wsHandlerV1()
//        }

    }
}

fun main() {
    embeddedServer(CIO, port = 8080, module = Application::module).start(wait = true)
}
