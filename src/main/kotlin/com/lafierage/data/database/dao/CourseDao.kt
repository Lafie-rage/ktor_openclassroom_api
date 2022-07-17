package com.lafierage.data.database.dao

import com.lafierage.model.Course

interface CourseDao {

    suspend fun getTop(): List<Course>

    suspend fun get(id: Int): Course?

    suspend fun getAll(): List<Course>

    suspend fun addNewCourse(
        title: String,
        level: Int,
        isActive: Boolean,
        rank: Int
    ): Course?

    suspend fun delete(id: Int): Boolean
    
}