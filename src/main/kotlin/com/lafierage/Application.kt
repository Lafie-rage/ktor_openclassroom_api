package com.lafierage

import com.lafierage.data.DatabaseFactory
import io.ktor.server.engine.*
import io.ktor.server.tomcat.*
import com.lafierage.plugins.*

fun main() {
    embeddedServer(Tomcat, port = 8080, host = "0.0.0.0") {
        DatabaseFactory.init()
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
