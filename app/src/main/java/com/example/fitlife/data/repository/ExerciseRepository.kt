package com.example.fitlife.data.repository

import com.example.fitlife.data.remote.ExerciseApiService
import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.repository.ExerciseRepository
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor (
    private val exerciseRemoteDataSource: ExerciseApiService
) : ExerciseRepository {
    override suspend fun getExerciseByName(name: String): List<Exercise> {
        return exerciseRemoteDataSource.getExerciseByName(name)
    }

    override suspend fun getAllExercises(): List<Exercise> {
        return exerciseRemoteDataSource.getAllExercises()
    }
}