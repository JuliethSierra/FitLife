package com.example.fitlife.domain.model.usecases

import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun invoke(email: String, password: String): User? =
        authRepository.login(email, password)
}
