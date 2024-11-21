package com.example.fitlife.domain.usecase

import com.example.fitlife.data.repository.ExerciseRepository
import com.example.fitlife.domain.model.Exercise

class GetAllExercisesUseCase(private val repository: ExerciseRepository) {
    suspend operator fun invoke(): List<Exercise> {
        return repository.getAllExercises()
    }
}