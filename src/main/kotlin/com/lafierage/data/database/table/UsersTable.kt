package com.lafierage.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable

object UsersTable: UUIDTable() {
    val pseudo = varchar("pseudo", 110)
    val password = text("password")
    val firstName = varchar("firstName", 50)
    val lastName = varchar("lastName", 50)
    val email = varchar("email", 150)
}