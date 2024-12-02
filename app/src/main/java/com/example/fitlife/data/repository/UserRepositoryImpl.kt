package com.example.fitlife.data.repository

import com.example.fitlife.data.local.dao.UserDao
import com.example.fitlife.data.local.mappers.toUser
import com.example.fitlife.data.local.mappers.toUsersEntity
import com.example.fitlife.data.remote.firebase.services.UserService
import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun insertUsers() {
        try {
            val users = userService.getAllDocuments()

            val usersEntityList = users.map { it.toUsersEntity() }

            userDao.insertUsers(usersEntityList)
        } catch (e: Exception) {
            println("Error al insertar usuarios: ${e.message}")
        }
    }

    override suspend fun getAllUsers(): List<User> {
        val userCommunity = userDao.getAllUsersCommunity()
        return userCommunity.map { it.toUser() }
    }

}