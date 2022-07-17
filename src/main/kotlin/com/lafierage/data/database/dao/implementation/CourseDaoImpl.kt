package com.lafierage.data.database.dao.implementation

import com.lafierage.data.DatabaseFactory.dbQuery
import com.lafierage.data.database.dao.CourseDao
import com.lafierage.model.Course
import com.lafierage.model.Courses
import org.jetbrains.exposed.sql.*

class CourseDaoImpl : CourseDao {

    private fun resultRowToCourse(row: ResultRow) = Course(
        id = row[Courses.id],
        title = row[Courses.title],
        level = row[Courses.level],
        isActive = row[Courses.isActive],
        rank = row[Courses.rank]
    )

    override suspend fun getTop(): List<Course> =
        dbQuery {
            Courses
                .select { Courses.rank greater 5 }
                .map(::resultRowToCourse)
        }

    override suspend fun get(id: Int): Course? =
        dbQuery {
            Courses
                .select { Courses.id eq id }
                .map(::resultRowToCourse)
                .singleOrNull()
        }

    override suspend fun getAll(): List<Course> =
        dbQuery {
            Courses
                .selectAll()
                .map(::resultRowToCourse)
        }

    override suspend fun addNewCourse(
        title: String,
        level: Int,
        isActive: Boolean,
        rank: Int
    ): Course? = dbQuery {
        val insertStatement = Courses.insert {
            it[Courses.title] = title
            it[Courses.level] = level
            it[Courses.isActive] = isActive
            it[Courses.rank] = rank
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCourse)
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        Courses.deleteWhere { Courses.id eq id} > 0
    }

}