package com.example.fitlife.data.repository

import com.example.fitlife.data.local.dao.UserDao
import javax.inject.Inject

class UserRepository  @Inject constructor(private val userDao: UserDao){

    /*suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers().map { it.toUser() }
    }*/
}

