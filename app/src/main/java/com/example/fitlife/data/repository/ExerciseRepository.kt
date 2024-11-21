package com.example.fitlife.data.repository

import com.example.fitlife.data.remote.ExerciseRemoteDataSource
import com.example.fitlife.domain.model.Exercise

interface ExerciseRepository {
    suspend fun getExerciseByName(name: String): List<Exercise>
    suspend fun getAllExercises(): List<Exercise>
}

class ExerciseRepositoryImpl(private val exerciseRemoteDataSource: ExerciseRemoteDataSource) : ExerciseRepository {
    override suspend fun getExerciseByName(name: String): List<Exercise> {
        return exerciseRemoteDataSource.getExerciseByName(name)
    }

    override suspend fun getAllExercises(): List<Exercise> {
        return exerciseRemoteDataSource.getAllExercises()
    }
}