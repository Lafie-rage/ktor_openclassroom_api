package com.lafierage.data

import com.lafierage.model.Course

class CourseDao {

    fun getTop(): List<Course> {
        return customStorage.filter {
            it.rank > 6
        }
    }

    fun get(id: Int): Course? {
       return customStorage.singleOrNull {
            it.id == id
        }
    }
}