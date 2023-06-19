package ru.otus.livescore.app

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.websocket.*
import ru.otus.livescore.app.plugins.initAppSettings

fun Application.module(appSettings: LSAppSettings = initAppSettings(), installPlugins: Boolean = true) {
    if (installPlugins) {
        install(WebSockets)
    }

}

fun main() {
    embeddedServer(CIO, port = 8080, module = Application::module).start(wait = true)
}
