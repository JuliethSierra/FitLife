package com.example.fitlife.domain.usecase

import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetAllExercisesUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(): List<Exercise> {
        return repository.getAllExercises()
    }
}