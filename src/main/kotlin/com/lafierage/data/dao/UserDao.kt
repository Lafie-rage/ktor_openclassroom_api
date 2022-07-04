package com.lafierage.data.dao

import com.lafierage.data.model.User

interface UserDao {

    suspend fun getAll(): List<User>

    suspend fun get(id: Int): User?

    suspend fun get(pseudo: String, password: String): User?

    suspend fun add(
        pseudo: String,
        password: String,
        firstName: String,
        lastName: String,
        email: String,
    )

    suspend fun delete(id: Int): Boolean

}