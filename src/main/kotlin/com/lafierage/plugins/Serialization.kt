package com.lafierage.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import java.lang.Math.abs

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
        })
    }
}

fun main() {
    val value = readLine()!!.toInt()
    if (abs(value) % 2 == 1) {
        println("ODD")
    } else {
        println("EVEN")
    }
}
