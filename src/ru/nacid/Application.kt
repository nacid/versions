package ru.nacid

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import ru.nacid.storage.MemoryStorage
import ru.nacid.storage.Storage

val storage: Storage = MemoryStorage()

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    install(ContentNegotiation) {
        gson {
        }
    }

    routing {
        get("/") {
            call.respond(storage.map)
        }

        post("/") {
            Version.from(call.parameters["version"])?.let {
                call.respond(storage.next(it).toString())
            } ?: let {
                call.respond(HttpStatusCode.BadRequest, "unexpected \"version\" value")
            }
        }
    }
}

