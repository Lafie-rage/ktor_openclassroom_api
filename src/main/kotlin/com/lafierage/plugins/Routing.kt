package com.lafierage.plugins

import com.lafierage.data.dao.CourseDao
import com.lafierage.data.dao.CourseDaoImpl
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.runBlocking

fun Application.configureRouting() {

    val courseDao: CourseDao = CourseDaoImpl().apply {
        runBlocking {
            if(getAll().isEmpty()) {
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
        route("/course") {
            get("top"){
                call.respond(courseDao.getTop())
            }
            get("{id?}") {
                val id = call.parameters["id"] ?: return@get call.respondText(
                    "Missing id",
                    status = HttpStatusCode.BadRequest,
                )
                courseDao.get(id.toInt())?.let {course ->
                    call.respond(course)
                } ?: call.respondText(
                    "Sorry ! No course were found...",
                    status = HttpStatusCode.NotFound,
                )
            }

        }
    }
    routing {
    }
}
