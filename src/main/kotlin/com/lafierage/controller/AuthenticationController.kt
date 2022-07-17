package com.lafierage.controller

import com.lafierage.data.database.dao.AuthenticationDao
import com.lafierage.response.AuthenticationResponse
import io.ktor.server.plugins.*

class AuthenticationController(
    private val authenticationDao: AuthenticationDao,
    private val jwtController: JwtController,
    private val encryptor: (String) -> String,
) {
    suspend fun register(
        pseudo: String,
        password: String,
        firstName: String,
        lastName: String,
        email: String,
    ) = try {
        validateRegisterCredentialsOrThrow(pseudo, password, firstName, lastName, email)
        if (!authenticationDao.isEmailTaken(email)) {
            throw BadRequestException("Email already taken! Input another email")
        }
        val user = authenticationDao.register(
            pseudo = pseudo,
            password = encryptor(password),
            firstName = firstName,
            lastName = lastName,
            email = email,
        )
        AuthenticationResponse.success(
            jwtController.generateToken(
                user.id
            ),
            "Registered successfully"
        )
    } catch (e: BadRequestException) {
        AuthenticationResponse.failed(e.message!!)
    }

    suspend fun authenticate(pseudo: String, password: String): AuthenticationResponse {
        return try {
            validateLCredentialsOrThrowException(pseudo, password)

            val user = authenticationDao.authenticate(pseudo, encryptor(password))
                ?: throw BadRequestException("Invalid credentials")

            AuthenticationResponse.success(jwtController.generateToken(user.id), "Logged in successfully")
        } catch (e: BadRequestException) {
            AuthenticationResponse.failed(e.message!!)
        }
    }
}

@Throws(BadRequestException::class)
fun validateRegisterCredentialsOrThrow(
    pseudo: String,
    password: String,
    firstName: String,
    lastName: String,
    email: String,
) {
    val message = when {
        (pseudo.isBlank() or password.isBlank()) -> "Pseudo or password should not be blank"
        (email.isBlank()) -> "Email cannot not be blank"
        (firstName.isBlank()) -> "First name cannot be blank"
        (lastName.isBlank()) -> "Last name cannot be blank"
        (pseudo.length !in (4..30)) -> "Pseudo should be of min 4 and max 30 characters in length"
        (password.length !in (8..50)) -> "Password should be of min 8 and max 50 characters in length"
        (firstName.length !in (2..50)) -> "First name should be of min 2 and max 50 characters in length"
        (lastName.length !in (2..50)) -> "Last name should be of min 2 and max 50 characters in length"
        else -> return
    }

    throw BadRequestException(message)
}

fun validateLCredentialsOrThrowException(
    password: String,
    pseudo: String,
) {
    val message = when {
        (pseudo.isBlank()) -> "Pseudo cannot not be blank"
        (password.isBlank()) -> "Password should not be blank"
        (pseudo.length !in (4..30)) -> "Pseudo should be of min 4 and max 30 character in length"
        (password.length !in (8..50)) -> "Password should be of min 8 and max 50 character in length"
        else -> return
    }
    throw BadRequestException(message)
}