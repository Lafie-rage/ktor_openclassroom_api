package com.lafierage.data.dao.implementation

import com.lafierage.data.DatabaseFactory.dbQuery
import com.lafierage.data.dao.UserDao
import com.lafierage.data.model.User
import com.lafierage.data.model.Users
import org.jetbrains.exposed.sql.*

class UserDaoImpl : UserDao {

    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        pseudo = row[Users.pseudo],
        password = row[Users.password],
        firstName = row[Users.firstName],
        lastName = row[Users.lastName],
        email = row[Users.email],
    )

    override suspend fun getAll(): List<User> = dbQuery {
        Users
            .selectAll()
            .map(::resultRowToUser)
    }

    override suspend fun get(id: Int): User? =
        dbQuery {
            Users
                .select { Users.id eq id }
                .map(::resultRowToUser)
                .singleOrNull()
        }

    override suspend fun get(pseudo: String, password: String): User? =
        dbQuery {
            Users
                .select { (Users.pseudo eq pseudo) and (Users.password like password) }
                .map(::resultRowToUser)
                .singleOrNull()
        }

    override suspend fun add(
        pseudo: String,
        password: String,
        firstName: String,
        lastName: String,
        email: String
    ) = dbQuery {
        val insertStatement = Users.insert {
            it[Users.pseudo] = pseudo
            it[Users.password] = password
            it[Users.firstName] = firstName
            it[Users.lastName] = lastName
            it[Users.email] = email
        }
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }
}