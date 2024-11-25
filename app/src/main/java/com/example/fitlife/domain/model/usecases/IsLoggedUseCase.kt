package com.example.fitlife.domain.model.usecases

import android.util.Log
import com.example.fitlife.domain.repository.AuthRepository
import javax.inject.Inject

class IsLoggedUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun invoke(): Boolean {
        val response = authRepository.isLogged()
        Log.d("AndroidRuntime", "Response UC: $response")
        return response
    }
}
