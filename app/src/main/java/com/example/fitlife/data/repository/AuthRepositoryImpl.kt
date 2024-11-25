package com.example.fitlife.data.repository

import android.util.Log
import com.example.fitlife.data.local.dao.UserDao
import com.example.fitlife.data.local.mappers.toUserEntity
import com.example.fitlife.data.remote.firebase.services.AuthenticationService
import com.example.fitlife.data.remote.firebase.services.UserService
import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService,
    private val userDao: UserDao
) : AuthRepository {


    override suspend fun login(email: String, password: String): User? {
        val uid = authenticationService.login(email, password)
        return if (uid != null) {
            val user = userService.getUserByUid(uid)
            if (user != null) {
                try {
                    userDao.insertUser(user.toUserEntity())
                    user
                } catch (e: Exception) {
                    null
                }
            } else {
                null
            }
        } else {
            null
        }
    }

    override suspend fun signOut() = userDao.deleteAllUsers()

    override suspend fun isLogged(): Boolean {
        Log.d("AndroidRuntime", "Into repo")

        val response = userDao.getAllUsers()

        return if (response.isNotEmpty()) {
            Log.d("AndroidRuntime", "Is logged? true")
            true
        } else {
            Log.d("AndroidRuntime", "Is logged? false")
            false
        }
    }
}

