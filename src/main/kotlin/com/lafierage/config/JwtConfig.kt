package com.lafierage.config

data class JwtConfig(
    val secret: String,
    val audience: String,
    val issuer: String,
)
