package com.example.fitlife.domain.model

import com.example.fitlife.domain.model.enums.GenderEnum

data class User(
    val name: String,
    val lastName: String,
    val email: String,
    val height: Float,
    val birthDate: String, // Fecha de nacimiento en formato ISO (e.g., "YYYY-MM-DD")
    val gender: GenderEnum,
    val weight: Float,
    val profilePictureUrl: String? = null,
    val numberPhone: String,
    val password: String,
    var uid: String = "",

)