package com.example.fitlife.domain.repository

import com.example.fitlife.data.remote.ExerciseRemoteDataSource
import com.example.fitlife.domain.model.Exercise

interface ExerciseRepository {
    suspend fun getExerciseByName(name: String): List<Exercise>
    suspend fun getAllExercises(): List<Exercise>
}