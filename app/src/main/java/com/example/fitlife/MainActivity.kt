package com.example.fitlife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.model.enums.GenderEnum
import com.example.fitlife.presentation.ui.screens.initScreen.InitScreen
import com.example.fitlife.presentation.viewmodel.UserViewModel
import com.example.fitlife.presentation.ui.screens.introduction.WelcomeScreen
import com.example.fitlife.presentation.ui.screens.login.LoginScreen
import com.example.fitlife.presentation.ui.screens.training.TrainingScreenWithViewModel
import com.example.fitlife.presentation.ui.screens.signin.SignInScreen
import com.example.fitlife.presentation.ui.screens.utils.Constants
import com.example.fitlife.presentation.ui.screens.utils.Util
import com.example.fitlife.presentation.viewmodel.LogInScreenViewModel
import com.example.fitlife.presentation.viewmodel.SignUpViewModel
import com.example.fitlife.presentation.ui.screens.training.TrainingScreenWithViewModel
import com.example.fitlife.ui.theme.FitLifeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private val logInViewModel: LogInScreenViewModel by viewModels()
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getUsers()
        setContent {
            val usersState = userViewModel.uiState.collectAsState()

            val navController = rememberNavController()
            FitLifeTheme {
                NavHost(
                    navController = navController,
                    startDestination = "introduction"
                ) {
                    composable("introduction") {
                        getUsers()
                        WelcomeScreen(
                            usersUiState = usersState,
                            onRegister = {
                                navController.navigate("SignIn")
                            },
                            onLogin = {
                                navController.navigate("LogIn")
                            }
                        )
                    }

                    composable("SignIn") {

                    }

                    composable("LogIn") {
                        LoginScreen(
                            logInViewModel = logInViewModel,
                            navController = navController
                            onLoginClick = {
                                navController.navigate("TrainingScreen")
                            }
                        )
                    }

                    composable("TrainingScreen") {
                        TrainingScreenWithViewModel()
                    }
                }
            }

        }
    }

    private fun getUsers() {
        userViewModel.loadUsers()
    }



    private suspend fun signUp(user: User): Boolean {
        return if (user.name.isNotEmpty() && user.lastName.isNotEmpty() && user.email.isNotEmpty() &&
            user.numberPhone.isNotEmpty() && user.password.isNotEmpty()
        ) {
            try {
                val isSignUpSuccessful = signUpViewModel.signUp(user)
                if (isSignUpSuccessful) {
                    Log.d(Constants.TAG, "Registro exitoso: ${user.email}")
                    Util.showAShortMessage("Registro exitoso", this)
                } else {
                    Util.showAShortMessage("Registro fallido", this)
                }
                isSignUpSuccessful
            } catch (e: Exception) {
                Log.e(Constants.TAG, "Error en el registro: ${e.message}")
                Util.showAShortMessage("Ocurrió un error durante el registro", this)
                false
            }
        } else {
            Util.showAShortMessage("Campos vacíos", this)
            false
        }
    }
}


