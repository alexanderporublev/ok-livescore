package ru.otus.otuskotlin.livescore.app.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.livescore.app.LSAppSettings
import ru.otus.livescore.app.v1.*

fun Route.v1Match(appSettings: LSAppSettings) {
    route("match") {
        post("create") {
            call.createMatch(appSettings)
        }
        post("read") {
            call.readMatch(appSettings)
        }
        post("update") {
            call.updateMatch(appSettings)
        }
        post("delete") {
            call.deleteMatch(appSettings)
        }
        post("matches") {
            call.readMatches(appSettings)
        }
    }
}


