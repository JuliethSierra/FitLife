package com.example.fitlife.domain.repository

import com.example.fitlife.data.remote.ExerciseRemoteDataSource
import com.example.fitlife.domain.model.Exercise

interface ExerciseRepository {
    suspend fun getExerciseByName(name: String): List<Exercise>
}

class ExerciseRepositoryImpl(private val exerciseRemoteDataSource: ExerciseRemoteDataSource.ExerciseRemoteDataSource) : ExerciseRepository {
    override suspend fun getExerciseByName(name: String): List<Exercise> {
        return exerciseRemoteDataSource.getExerciseByName(name)
    }
}