package com.example.fitlife

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fitlife.presentation.ui.screens.advances.AdvancesScreen
import com.example.fitlife.presentation.ui.screens.introduction.SplashScreen
import com.example.fitlife.presentation.viewmodel.UserViewModel
import com.example.fitlife.presentation.ui.screens.introduction.IntroductionScreen
import com.example.fitlife.presentation.ui.screens.login.LoginScreen
import com.example.fitlife.presentation.ui.screens.menu.BottomNavigationBar
import com.example.fitlife.presentation.ui.screens.profilescreen.ProfileScreen
import com.example.fitlife.presentation.ui.screens.initscreen.InitScreen
import com.example.fitlife.presentation.ui.screens.training.TrainingScreenWithViewModel
import com.example.fitlife.presentation.ui.screens.signup.SignInScreen
import com.example.fitlife.presentation.ui.screens.training.ExerciseDetailScreen
import com.example.fitlife.presentation.viewmodel.LogInScreenViewModel
import com.example.fitlife.presentation.viewmodel.SignUpViewModel
import com.example.fitlife.ui.theme.FitLifeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private val logInViewModel: LogInScreenViewModel by viewModels()
    private val signUpViewModel: SignUpViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.P)
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
                                "AdvancesScreen",
                                "InitScreen",
                                "ExerciseDetail/{exerciseName}"
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
                            SplashScreen(navController = navController, logInViewModel)
                        }

                        composable("introduction") {
                            IntroductionScreen(
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
                            TrainingScreenWithViewModel { exerciseName ->
                                navController.navigate("ExerciseDetail/$exerciseName")
                            }
                        }
                        composable("AdvancesScreen") {
                            AdvancesScreen()
                        }
                        composable("ProfileScreen") {
                            ProfileScreen(
                                logInViewModel = logInViewModel,
                                navController = navController
                            )
                        }

                        composable("InitScreen") {
                            InitScreen(navController = navController)
                        }

                        composable("ExerciseDetail/{exerciseName}", arguments = listOf(navArgument("exerciseName") { type = NavType.StringType })) { backStackEntry ->
                            val exerciseName = backStackEntry.arguments?.getString("exerciseName")
                            if (exerciseName != null) {
                                ExerciseDetailScreen(exerciseName = exerciseName)
                            }
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


