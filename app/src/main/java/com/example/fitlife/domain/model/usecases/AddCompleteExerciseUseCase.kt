package com.example.fitlife.domain.model.usecases

import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.repository.AuthRepository
import com.example.fitlife.domain.repository.ExerciseRepository
import javax.inject.Inject

class AddCompleteExerciseUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    suspend fun invoke(exerciseName: String): Boolean =
        exerciseRepository.addCompletedExercise(exerciseName)
}
