package com.lafierage.data.database.dao

import com.lafierage.data.dto.User
import java.util.UUID

interface AuthenticationDao {

    suspend fun getAll(): List<User>

    suspend fun get(id: UUID): User?

    suspend fun authenticate(pseudo: String, password: String): User?

    suspend fun isPseudoTaken(pseudo: String): Boolean

    suspend fun isEmailTaken(email: String): Boolean

    suspend fun register(
        pseudo: String,
        password: String,
        firstName: String,
        lastName: String,
        email: String,
    ): User

    suspend fun delete(id: UUID): Boolean

}