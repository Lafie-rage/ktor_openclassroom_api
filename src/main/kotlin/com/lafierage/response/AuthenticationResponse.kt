package com.lafierage.response

import com.lafierage.response.Response.Companion.FAILED
import com.lafierage.response.Response.Companion.SUCCESS
import com.lafierage.response.Response.Companion.UNAUTHORIZED

@kotlinx.serialization.Serializable
class AuthenticationResponse(
    override val status: String,
    override val message: String,
    val token: String? = null,
) : Response {

    companion object {
        fun failed(message: String) = AuthenticationResponse(
            FAILED,
            message
        )

        fun unauthorized(message: String) = AuthenticationResponse(
            UNAUTHORIZED,
            message
        )

        fun success(token: String, message: String) = AuthenticationResponse(
            SUCCESS,
            message,
            token
        )
    }

}