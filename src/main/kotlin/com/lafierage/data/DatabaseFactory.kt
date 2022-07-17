package com.lafierage.data

import com.lafierage.config.DatabaseLogin
import com.lafierage.config.retrieveDatabaseLogin
import com.lafierage.data.database.table.UsersTable
import com.lafierage.model.Courses
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val driverClassName = "com.mysql.cj.jdbc.Driver"
        val jdbcUrl = "jdbc:mysql://localhost:3306/course"
        val login: DatabaseLogin = retrieveDatabaseLogin()
        val database = Database.connect(jdbcUrl, driverClassName, login.username, login.password)
        transaction(database) {
            SchemaUtils.create(Courses)
            SchemaUtils.create(UsersTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}