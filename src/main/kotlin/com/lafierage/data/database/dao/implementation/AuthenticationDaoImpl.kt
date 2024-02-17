package com.lafierage.data.database.dao.implementation

import com.lafierage.data.DatabaseFactory.dbQuery
import com.lafierage.data.database.dao.AuthenticationDao
import com.lafierage.data.database.entity.UserEntity
import com.lafierage.data.database.table.UsersTable
import com.lafierage.data.dto.User
import com.lafierage.data.dto.User.Companion.fromEntity
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import java.util.*

class AuthenticationDaoImpl : AuthenticationDao {

    override suspend fun getAll(): List<User> = dbQuery {
        UserEntity
            .all()
            .map(::fromEntity)
    }

    override suspend fun get(id: UUID): User? =
        dbQuery {
            UserEntity
                .findById(id)?.let(::fromEntity)
        }

    override suspend fun authenticate(pseudo: String, password: String): User? =
        dbQuery {
            UserEntity
                .find {
                    (UsersTable.pseudo eq pseudo) and (UsersTable.password eq password)
                }
                .map(::fromEntity)
                .singleOrNull()
        }

    override suspend fun isPseudoTaken(pseudo: String): Boolean = dbQuery {
        UserEntity.find {
            UsersTable.pseudo eq pseudo
        }.firstOrNull() == null
    }

    override suspend fun isEmailTaken(email: String): Boolean = dbQuery {
        UserEntity.find {
            UsersTable.email eq email
        }.firstOrNull() == null
    }

    override suspend fun register(
        pseudo: String,
        password: String,
        firstName: String,
        lastName: String,
        email: String
    ) = dbQuery {
        UserEntity.new {
            this.pseudo = pseudo
            this.password = password
            this.firstName = firstName
            this.lastName = lastName
            this.email = email
        }.let(::fromEntity)
    }

    override suspend fun delete(id: UUID): Boolean = dbQuery {
        UsersTable.deleteWhere { UsersTable.id eq id } > 0
    }
}