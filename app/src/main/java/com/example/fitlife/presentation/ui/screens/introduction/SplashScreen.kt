package com.example.fitlife.presentation.ui.screens.introduction

import android.util.Log
import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.fitlife.R
import com.example.fitlife.presentation.viewmodel.LogInScreenViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    logInViewModel: LogInScreenViewModel
) {
    val uiState by logInViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        delay(3000)
        logInViewModel.isLogged()
    }

    Splash()

    LaunchedEffect(key1 = uiState.isFinished) {
        Log.d("AndroidRuntime", "Prev to review the session")

        if (uiState.isFinished) {
            Log.d("AndroidRuntime", "Start to review the session")
            navController.popBackStack()
            if (uiState.isLogged) {
                Log.d("AndroidRuntime", "InitScreen")
                navController.navigate("InitScreen")
            } else {
                Log.d("AndroidRuntime", "introduction")
                navController.navigate("introduction")
            }
        }
    }
}

@Composable
fun Splash() {
    ConstraintLayout (
        modifier = Modifier.fillMaxSize()
    ){
        val (logo, title) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.splash_icon),
            contentDescription = "Logo",
            modifier = Modifier
                .size(150.dp)
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(title.top, margin = 100.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Text(
            text = "",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(logo.bottom, margin = 100.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        createVerticalChain(logo, title, chainStyle = ChainStyle.Packed)
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Splash()
}