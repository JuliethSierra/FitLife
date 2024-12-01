package com.example.fitlife.domain.repository

import com.example.fitlife.data.local.entity.ExerciseEntity
import com.example.fitlife.domain.model.Exercise

interface ExerciseRepository {
    suspend fun getExerciseByName(name: String): List<Exercise>
    suspend fun getAllExercises(): List<Exercise>
    suspend fun getAllLocalExercises(): List<Exercise>
    suspend fun addExercises(exercise: List<Exercise>)
    suspend fun addCompletedExercise(exerciseName: String): Boolean
    suspend fun getCompletedExercises(): List<String>
}