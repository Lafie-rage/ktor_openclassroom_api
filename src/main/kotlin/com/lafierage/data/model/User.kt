package com.lafierage.data.model

import com.lafierage.data.config.ENCRYPTING_CONFIGURATION
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.crypt.Algorithms
import org.jetbrains.exposed.crypt.encryptedVarchar
import org.jetbrains.exposed.sql.Table

@Serializable
data class User(
    val id: Int,
    val pseudo: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String,
)

object Users : Table() {
    private val encryptor  = Algorithms.AES_256_PBE_CBC(
        ENCRYPTING_CONFIGURATION.password,
        ENCRYPTING_CONFIGURATION.salt
    )

    val id = integer("id").autoIncrement()
    val pseudo = varchar("pseudo", 110)
    val password = encryptedVarchar(
        "password",
        100,
        encryptor,
    )

    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val email = varchar("email", 150)

    override val primaryKey = PrimaryKey(id)
}
