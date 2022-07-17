package com.lafierage

import com.lafierage.config.ENCRYPTING_CONFIGURATION
import com.lafierage.controller.AuthenticationController
import com.lafierage.controller.JwtController
import com.lafierage.data.DatabaseFactory
import com.lafierage.data.database.dao.implementation.AuthenticationDaoImpl
import io.ktor.server.engine.*
import io.ktor.server.tomcat.*
import com.lafierage.plugins.*
import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

fun main() {
    embeddedServer(Tomcat, port = 8080, host = "0.0.0.0") {
        DatabaseFactory.init()

        val jwtController = JwtController()
        val encryptor = {s: String -> encrypt(s)}
        val authenticationDao = AuthenticationDaoImpl()

        val authenticationController = AuthenticationController(
            encryptor = encryptor,
            authenticationDao = authenticationDao,
            jwtController = jwtController,
        )

        configureSerialization()
        configureSecurity(
            authenticationDao = authenticationDao,
            jwtController = jwtController,
        )
        configureRouting(authenticationController = authenticationController)
    }.start(wait = true)
}

const val ALGO = "HmacSHA256"

val hashKey = ENCRYPTING_CONFIGURATION.secret.toByteArray()
val hmacKey = SecretKeySpec(hashKey, ALGO)

// method for password encryption
fun encrypt(password: String): String {
    val hmac = Mac.getInstance(ALGO)
    hmac.init(hmacKey)
    return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
}