package com.example.fitlife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitlife.presentation.ui.screens.initScreen.InitScreen
import com.example.fitlife.presentation.viewmodel.UserViewModel
import com.example.fitlife.presentation.ui.screens.introduction.WelcomeScreen
import com.example.fitlife.presentation.ui.screens.login.LoginScreen
import com.example.fitlife.ui.theme.FitLifeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()

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
                            onLoginClick = {
                                navController.navigate("InitScreen")
                            }
                        )
                    }

                    composable("InitScreen") {
                        InitScreen()
                    }
                }
            }

        }
    }

    private fun getUsers() {
        userViewModel.loadUsers()
    }

}


