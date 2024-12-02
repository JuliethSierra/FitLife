package com.example.fitlife.data.repository

import android.util.Log
import com.example.fitlife.data.local.dao.UserDao
import com.example.fitlife.data.local.mappers.toUser
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

    companion object {
        var currentUser: User? = null
    }

    override suspend fun login(email: String, password: String): User? {
        val uid = authenticationService.login(email, password)
        return if (uid != null) {
            val user = userService.getUserByUid(uid)
            if (user != null) {
                try {
                    userDao.insertUser(user.toUserEntity())
                    currentUser = user // Asignar al companion object
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
            currentUser = response[0].toUser()
            Log.d("AndroidRuntime", "Is logged? true")
            true
        } else {
            Log.d("AndroidRuntime", "Is logged? false")
            false
        }
    }

    override suspend fun updateUser(user: User): Boolean {
        return try {
            // Actualizar en Firebase
            val firebaseUpdated = userService.updateUser(user)

            // Actualizar en Room si Firebase fue exitoso
            if (firebaseUpdated) {
                userDao.insertUser(user.toUserEntity()) // Convierte el modelo y lo guarda en Room
            }

            firebaseUpdated
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Error al actualizar usuario: ${e.message}")
            false
        }
    }
}


