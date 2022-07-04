package com.lafierage.data.model

import com.lafierage.data.config.ENCRYPTING_CONFIGURATION
import org.jetbrains.exposed.crypt.Algorithms
import org.jetbrains.exposed.crypt.encryptedVarchar
import org.jetbrains.exposed.sql.Table

data class User(
    val id: Int,
    val pseudo: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String,
)

object Users: Table() {
    val id = integer("id").autoIncrement()
    val pseudo = varchar("pseudo", 110)
    val password = encryptedVarchar(
        "password",
        100,
        Algorithms.AES_256_PBE_CBC(
            ENCRYPTING_CONFIGURATION.password,
            ENCRYPTING_CONFIGURATION.salt
        )
    )
    val firstName = varchar("firstName", 50)
    val lastName = varchar("lastName", 50)
    val email = varchar("email", 150)

    override val primaryKey = PrimaryKey(id)
}
