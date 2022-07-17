package com.lafierage.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.lafierage.config.JWT_CONFIGURATION
import java.util.*

class JwtController {
    val SECRET = JWT_CONFIGURATION.secret
    private val audience = JWT_CONFIGURATION.audience
    private val issuer = JWT_CONFIGURATION.issuer

    /**
     * Generate a new token with the providing data.
     *
     * @param data The token's data
     */
    fun generateToken(data: String): String = JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("id", data)
        .withExpiresAt(Date(System.currentTimeMillis() + 60_000L * 60))
        .sign(Algorithm.HMAC256(SECRET))

    fun verifyToken(): JWTVerifier = JWT.require(Algorithm.HMAC256(SECRET))
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

}