package com.lafierage.response

import com.lafierage.response.Response.Companion.NOT_FOUND
import com.lafierage.response.Response.Companion.SUCCESS
import com.lafierage.response.Response.Companion.UNAUTHORIZED
import io.ktor.http.*

sealed class HttpResponse<T : Response> {
    abstract val body: T
    abstract val code: HttpStatusCode

    data class Ok<T : Response>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.OK
    }

    data class NotFound<T : Response>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.NotFound
    }

    data class BadRequest<T : Response>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.BadRequest
    }

    data class Unauthorized<T : Response>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.Unauthorized
    }

    companion object {
        fun <T : Response> ok(response: T) = Ok(body = response)

        fun <T : Response> notFound(response: T) = NotFound(body = response)

        fun <T : Response> badRequest(response: T) = BadRequest(body = response)

        fun <T : Response> unauthorize(response: T) = Unauthorized(body = response)
    }
}

/**
 * Generates [HttpResponse] from [Response class].
 */
fun generateHttpResponse(response: Response): HttpResponse<Response> {
    return when (response.status) {
        SUCCESS -> HttpResponse.ok(response)
        NOT_FOUND -> HttpResponse.notFound(response)
        UNAUTHORIZED -> HttpResponse.unauthorize(response)
        else -> HttpResponse.badRequest(response)
    }
}
