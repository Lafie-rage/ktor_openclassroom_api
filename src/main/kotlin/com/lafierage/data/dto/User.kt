package com.lafierage.data.dto

import com.lafierage.data.database.entity.UserEntity

data class User(
    val id: String,
    val pseudo: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String,
) {
    companion object {
        fun fromEntity(entity: UserEntity) = User(
            id = entity.id.value.toString(),
            pseudo = entity.pseudo,
            password = entity.password,
            firstName = entity.firstName,
            lastName = entity.lastName,
            email = entity.email,
        )
    }
}