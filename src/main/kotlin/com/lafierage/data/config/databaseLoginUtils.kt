package com.lafierage.data.config

import com.google.gson.Gson
import java.io.File

private const val CONFIG_FILE_PATH = "./src/main/resources/database_login.json"

fun retrieveDatabaseLogin(): DatabaseLogin =
    Gson().fromJson(File(CONFIG_FILE_PATH).readText(), DatabaseLogin::class.java)