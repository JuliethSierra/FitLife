package com.example.fitlife.domain.model.usecases

import com.example.fitlife.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun invoke() = authRepository.signOut()
}
