package com.example.fitlife.domain.usecase

import com.example.fitlife.data.repository.ExerciseRepository
import com.example.fitlife.domain.model.Exercise

class GetExerciseByNameUseCase(private val repository: ExerciseRepository) {
    suspend operator fun invoke(name: String): List<Exercise> {
        return repository.getExerciseByName(name)
    }
}