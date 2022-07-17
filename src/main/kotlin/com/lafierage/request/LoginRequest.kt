package com.lafierage.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val pseudo: String,
    val password: String,
)