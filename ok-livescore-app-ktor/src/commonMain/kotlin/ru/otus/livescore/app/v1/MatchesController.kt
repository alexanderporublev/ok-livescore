package ru.otus.livescore.app.v1

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.api.v1.models.*
import ru.otus.livescore.app.LSAppSettings
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.marketplace.mappers.v1.*
import ru.otus.otuskotlin.marketplace.api.v1.*

suspend fun ApplicationCall.createMatch(appSettings: LSAppSettings) {
    try {

        val processor = appSettings.processor
        //val request = receive<MatchCreateRequest>()
        val json = receiveText()
        println(json)
        val request = apiV1RequestDeserialize<MatchCreateRequest>(json)
        println("reqid")
        val context = LsContext()
        context.fromTransport(request)
        println("reqid" + request.requestId)
        processor.exec(context)
        respond(context.toTransportCreate())
    }
    catch (ex : CannotTransformContentToTypeException){
        println(ex.message)
    }
}

suspend fun ApplicationCall.readMatch(appSettings: LSAppSettings) {
    val processor = appSettings.processor
    val json = receiveText()
    println(json)
    val request = apiV1RequestDeserialize<MatchReadRequest>(json)
    println("reqid")
   // val request = receive<>()
    val context = LsContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportRead())
}

suspend fun ApplicationCall.updateMatch(appSettings: LSAppSettings) {
    val processor = appSettings.processor
    val json = receiveText()
    println(json)
    val request = apiV1RequestDeserialize<MatchUpdateRequest>(json)
    println("eve " + request.match?.eventId)
    val context = LsContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.deleteMatch(appSettings: LSAppSettings) {
    val processor = appSettings.processor
    val request = receive<MatchDeleteRequest>()
    val context = LsContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportDelete())
}

suspend fun ApplicationCall.readMatches(appSettings: LSAppSettings) {
    val processor = appSettings.processor
    val request = receive<MatchesRequest>()
    val context = LsContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportMatches())
}
