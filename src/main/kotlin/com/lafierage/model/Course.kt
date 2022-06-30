package com.lafierage.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

// https://ktor.io/docs/interactive-website-add-persistence.html#create_table

@Serializable
data class Course(
    val id: Int,
    val title: String,
    val level: Int,
    val isActive: Boolean,
    val rank: Int,
)

object Courses : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val level = integer("level")
    val isActive = byte("isActive")
    val rank = integer("rank")

    override val primaryKey = PrimaryKey(id)
}
