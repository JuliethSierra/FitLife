package com.example.fitlife.domain.model

import com.example.fitlife.domain.model.enums.GenderEnum

data class User(
    val name: String,
    val lastName: String,
    val email: String = "",
    val height: Float = 0.0f,
    val birthDate: String = "",
    val gender: GenderEnum = GenderEnum.MALE,
    val weight: Float = 0.0f,
    val profilePictureUrl: String? = null,
    val numberPhone: String = "",
    val password: String = "",
    var uid: String = "",
    var completedExercises: List<String>? = emptyList()
)
