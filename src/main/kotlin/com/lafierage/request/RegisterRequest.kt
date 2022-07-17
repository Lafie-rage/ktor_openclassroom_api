package com.lafierage.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val pseudo: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String,
)