package com.example.fitlife.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val uid: String,
    val name: String,
    val lastName: String,
    val email: String,
    val height: Float,
    val birthDate: String,
    val gender: String,
    val weight: Float,
    val profilePictureUrl: String? = null,
    val numberPhone: String,
    val password: String,
    val completedExercises: List<String>? = null
)
