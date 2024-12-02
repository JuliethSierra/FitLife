package com.example.fitlife.domain.usecase

import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.repository.UserRepository
import javax.inject.Inject

class GetAllUsersCommunityUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun invoke(): List<User>{
        userRepository.insertUsers()
        return userRepository.getAllUsers()
    }

}