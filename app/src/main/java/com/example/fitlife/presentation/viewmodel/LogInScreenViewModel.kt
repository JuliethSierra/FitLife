package com.example.fitlife.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.model.usecases.LoginUseCase
import com.example.fitlife.domain.model.usecases.SignUpUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

    @HiltViewModel
    class LogInScreenViewModel @Inject constructor(
        private val loginUseCase: LoginUseCase
    ) : ViewModel() {
        // Modificamos login para aceptar un callback con éxito o error
        fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
            viewModelScope.launch {
                try {
                    val result = loginUseCase.invoke(email, password)
                    // Si el login es exitoso
                    onResult(true, null)
                } catch (e: Exception) {
                    // Si ocurre un error durante el login
                    onResult(false, e.message)
                }
            }
        }
    }
