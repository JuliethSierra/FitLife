package com.example.fitlife.data.repository

import com.example.fitlife.data.local.dao.UserDao
import com.example.fitlife.data.local.entity.ExerciseEntity
import com.example.fitlife.data.local.mappers.toExercise
import com.example.fitlife.data.local.mappers.toExerciseEntity
import com.example.fitlife.data.remote.ExerciseApiService
import com.example.fitlife.data.remote.firebase.FirebaseClient
import com.example.fitlife.data.remote.firebase.services.UserService
import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.repository.ExerciseRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseRemoteDataSource: ExerciseApiService,
    private val userService: UserService,
    private val userDao: UserDao
) : ExerciseRepository {

    override suspend fun getExerciseByName(name: String): List<Exercise> {
        return exerciseRemoteDataSource.getExerciseByName(name)
    }

    override suspend fun getAllExercises(): List<Exercise> {
        var exercises = getAllLocalExercises()
        if (exercises.isEmpty()) {
            val remoteExercises = exerciseRemoteDataSource.getAllExercises()
            addExercises(remoteExercises)
            exercises = getAllLocalExercises()
        }
        return exercises
    }

    override suspend fun getAllLocalExercises(): List<Exercise> {
        val localExercises = userDao.getAllLocalExercises()
        val exercises = localExercises.map { it.toExercise() }
        return exercises
    }

    override suspend fun addExercises(exercise: List<Exercise>) {
        exercise.map { userDao.insertExercises(it.toExerciseEntity()) }
    }

    override suspend fun addCompletedExercise(exerciseName: String): Boolean =
        userService.addCompletedExercise(AuthRepositoryImpl.currentUser?.uid ?: "", exerciseName)

    override suspend fun getCompletedExercises(): List<String> =
        userService.getCompletedExercises(AuthRepositoryImpl.currentUser?.uid ?: "")
}