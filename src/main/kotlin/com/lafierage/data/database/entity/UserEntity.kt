package com.lafierage.data.database.entity

import com.lafierage.data.database.table.UsersTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(UsersTable)

    var pseudo by UsersTable.pseudo
    var password by UsersTable.password
    var firstName by UsersTable.firstName
    var lastName by UsersTable.lastName
    var email by UsersTable.email
}