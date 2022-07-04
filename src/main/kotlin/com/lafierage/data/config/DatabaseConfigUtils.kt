package com.lafierage.data.config

import com.google.gson.Gson
import java.io.File

private const val CONFIG_FILE_PATH = "./src/main/resources/database_config.json"

fun retrieveDatabaseLogin(): DatabaseLogin =
    Gson().fromJson(File(CONFIG_FILE_PATH).readText(), DatabaseLogin::class.java)

val ENCRYPTING_CONFIGURATION: EncryptingConfig =
    Gson().fromJson(File(CONFIG_FILE_PATH).readText(), EncryptingConfig::class.java)