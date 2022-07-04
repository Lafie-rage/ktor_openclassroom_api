package com.lafierage.data.config

import com.google.gson.annotations.SerializedName

data class EncryptingConfig(
    @SerializedName("aes_password")
    val password: String,
    @SerializedName("aes_salt")
    val salt: String,
)
