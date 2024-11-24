package com.example.fitlife.domain.model.usecases

import com.example.fitlife.data.remote.firebase.services.AuthenticationService
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
) {

    suspend fun invoke(email: String, password: String): String? {
        return authenticationService.login(email, password)
    }
}
