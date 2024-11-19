package com.example.fitlife.domain.usecase

import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.repository.ExerciseRepository

class GetExerciseUseCase(private val exerciseRepository: ExerciseRepository) {
    suspend operator fun invoke(name: String): List<Exercise> {
        return exerciseRepository.getExerciseByName(name)
    }
}