package com.lafierage.config

import com.google.gson.annotations.SerializedName

data class EncryptingConfig(
    @SerializedName("hmac_secret")
    val secret: String,
)
