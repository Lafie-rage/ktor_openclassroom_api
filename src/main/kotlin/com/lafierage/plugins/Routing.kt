package com.lafierage.plugins

import com.lafierage.data.dao.CourseDao
import com.lafierage.data.dao.CourseDaoImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kotlinx.coroutines.runBlocking

fun Application.configureRouting() {

    val courseDao: CourseDao = CourseDaoImpl().apply {
        runBlocking { // Init database for testing purposes
            if (getAll().isEmpty()) {
                addNewCourse(
                    title = "How to troll a Troll ?",
                    level = 5,
                    isActive = true,
                    rank = 10,
                )
                addNewCourse(
                    title = "Kotlin for troll",
                    level = 1,
                    isActive = true,
                    rank = 5,
                )
            }
        }
    }

    // Starting point for a Ktor app:
    routing {

        get("/") {
            call.respondText("Welcome to OpenClassrooms brand new server!")
        }

        route("/courses") {
            // region GET routes
            get {
                call.respond(courseDao.getAll())
            }

            get("top") {
                call.respond(courseDao.getTop())
            }

            get("{id?}") {
                val id = call.parameters["id"] ?: return@get call.respondText(
                    "Missing id",
                    status = HttpStatusCode.BadRequest,
                )
                courseDao.get(id.toInt())?.let { course ->
                    call.respond(course)
                } ?: call.respondText(
                    "Sorry ! No course were found...",
                    status = HttpStatusCode.NotFound,
                )
            }
            // endregion

            // region POST routes
            post {
                val formParameters = call.receiveParameters()
                try {
                    val createdCourse = courseDao.addNewCourse(
                        formParameters.getOrFail("title"),
                        formParameters.getOrFail("level").toInt(),
                        formParameters.getOrFail("isActive").toBooleanStrict(),
                        formParameters.getOrFail("rank").toInt(),
                    )
                    createdCourse?.let {
                        call.respond(createdCourse)
                    } ?: call.respondText(
                        "Error while creating item",
                        status = HttpStatusCode.InternalServerError,
                    )
                } catch (e: MissingRequestParameterException) {
                    call.respondText(
                        "Missing parameter : ${e.parameterName}",
                        status = HttpStatusCode.BadRequest,
                    )
                }
            }
            // endregion

            // region DELETE routes
            delete("{id?}") {
                val id = call.parameters["id"]?.toInt() ?: return@delete call.respond(HttpStatusCode.BadRequest)
                if (courseDao.get(id) != null) {
                    if (courseDao.delete(id)) {
                        call.respondText(
                            "",
                            status = HttpStatusCode.NoContent,
                        )
                    } else {
                        call.respondText(
                            "Error while deleting item",
                            status = HttpStatusCode.InternalServerError,
                        )
                    }
                } else {
                    call.respondText(
                        "This item doesn't exist",
                        status = HttpStatusCode.NotFound,
                    )
                }
            }
            // endregion
        }
    }
    routing {
    }
}
