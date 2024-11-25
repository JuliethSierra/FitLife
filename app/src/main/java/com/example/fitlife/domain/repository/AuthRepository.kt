package com.example.fitlife.domain.repository

import com.example.fitlife.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): User?
    suspend fun signOut()
    suspend fun isLogged(): Boolean
}