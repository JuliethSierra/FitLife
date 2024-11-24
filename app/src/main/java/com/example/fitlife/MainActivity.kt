package com.example.fitlife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fitlife.presentation.ui.screens.introduction.SplashScreen
import com.example.fitlife.presentation.viewmodel.UserViewModel
import com.example.fitlife.presentation.ui.screens.introduction.WelcomeScreen
import com.example.fitlife.presentation.ui.screens.login.LoginScreen
import com.example.fitlife.presentation.ui.screens.menu.BottomNavigationBar
import com.example.fitlife.presentation.ui.screens.profilescreen.ProfileScreen
import com.example.fitlife.presentation.ui.screens.initScreen.InitScreen
import com.example.fitlife.presentation.ui.screens.training.TrainingScreenWithViewModel
import com.example.fitlife.presentation.ui.screens.signin.SignInScreen
import com.example.fitlife.presentation.viewmodel.LogInScreenViewModel
import com.example.fitlife.presentation.viewmodel.SignUpViewModel
import com.example.fitlife.ui.theme.FitLifeTheme
import dagger.hilt.android.AndroidEntryPoint

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
                Scaffold(
                    bottomBar = {
                        val currentRoute = getCurrentRoute(navController)
                        if (currentRoute in listOf(
                                "TrainingScreen",
                                "ProfileScreen",
                                "InitScreen"
                            )
                        ) {
                            BottomNavigationBar(navController)
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "SplashScreen",
                        modifier = Modifier.padding(paddingValues)
                    ) {

                        // Pantallas sin BottomNavigationBar
                        composable("SplashScreen") {
                            SplashScreen(navController = navController)
                        }

                        composable("introduction") {
                            WelcomeScreen(
                                usersUiState = usersState,
                                onRegister = { navController.navigate("SignIn") },
                                onLogin = { navController.navigate("LogIn") }
                            )
                        }

                        composable("SignIn") {
                            SignInScreen(
                                signUpViewModel = signUpViewModel,
                                navController = navController
                            )
                        }

                        composable("LogIn") {
                            LoginScreen(
                                logInViewModel = logInViewModel,
                                navController = navController
                            )
                        }

                        // Pantallas con BottomNavigationBar
                        composable("TrainingScreen") {
                            TrainingScreenWithViewModel()
                        }

                        composable("ProfileScreen") {
                            ProfileScreen()
                        }

                        composable("InitScreen") {
                            InitScreen()
                        }
                    }
                }
            }
        }
    }

    private fun getUsers() {
        userViewModel.loadUsers()
    }

    @Composable
    private fun getCurrentRoute(navController: NavController): String? {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        return navBackStackEntry?.destination?.route
    }
}


