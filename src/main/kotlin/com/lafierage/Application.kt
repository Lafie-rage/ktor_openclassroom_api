package com.lafierage

import io.ktor.server.engine.*
import io.ktor.server.tomcat.*
import com.lafierage.plugins.*

fun main() {
    embeddedServer(Tomcat, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
