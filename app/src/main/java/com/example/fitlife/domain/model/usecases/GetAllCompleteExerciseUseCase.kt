package com.example.fitlife.domain.model.usecases

import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.repository.AuthRepository
import com.example.fitlife.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetAllCompleteExerciseUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    suspend fun invoke(): List<String> =
        exerciseRepository.getCompletedExercises()
}
