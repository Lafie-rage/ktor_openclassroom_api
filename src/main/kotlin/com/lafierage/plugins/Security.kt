package com.lafierage.plugins

import com.lafierage.controller.JwtController
import com.lafierage.data.database.dao.AuthenticationDao
import com.lafierage.data.dto.User
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.util.*

class UserPrincipal(val user: User): Principal

fun Application.configureSecurity(
    authenticationDao: AuthenticationDao,
    jwtController: JwtController
) {
    install(Authentication) {
        jwt("main_auth") {
            verifier(jwtController.verifyToken())
            validate { credential ->
                val userId = credential.payload.getClaim("id").asString()

                val user = authenticationDao.get(UUID.fromString(userId))

                user?.let(::UserPrincipal)
            }
        }
    }
}