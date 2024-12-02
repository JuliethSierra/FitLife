package com.example.fitlife.data.local.entity

import androidx.room.Entity
import com.example.fitlife.domain.model.User
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "users_table")
data class UsersEntity(
    @PrimaryKey val uid: String,
    val name: String,
    val lastName: String,
    val completedExercises: List<String>? = null
)
