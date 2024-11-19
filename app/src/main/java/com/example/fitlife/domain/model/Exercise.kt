package com.example.fitlife.domain.model

data class Exercise(
    val id: String,
    val name: String,
    val bodyPart: String,
    val equipment: String,
    val target: String,
    val gifUrl: String,
    val secondaryMuscles: String,
    val instructions: String
)
