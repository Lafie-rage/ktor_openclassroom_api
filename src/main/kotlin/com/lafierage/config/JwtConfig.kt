package com.lafierage.config

import com.google.gson.annotations.SerializedName

data class JwtConfig(
    @SerializedName("jwt_secret")
    val secret: String,
    @SerializedName("jwt_audience")
    val audience: String,
    @SerializedName("jwt_issuer")
    val issuer: String,
)
