package com.example.fitlife.data.remote

import com.example.fitlife.domain.model.Exercise


class ExerciseRemoteDataSource(private val apiService: ExerciseApiService) {

    suspend fun getExerciseByName(name: String): List<Exercise> {
        return apiService.getExerciseByName(name)
    }

    suspend fun getAllExercises(): List<Exercise> {
        return apiService.getAllExercises()
    }
}
