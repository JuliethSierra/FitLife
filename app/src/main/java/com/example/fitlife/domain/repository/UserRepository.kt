package com.example.fitlife.domain.repository

import com.example.fitlife.data.local.entity.UsersEntity
import com.example.fitlife.domain.model.User

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun insertUsers()
}