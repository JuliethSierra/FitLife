package com.example.fitlife.presentation.ui.screens.introduction

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.State
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.fitlife.presentation.ui.screens.states.UserUiState
import com.example.fitlife.ui.theme.purple
import com.example.fitlife.ui.theme.white

@Composable
fun IntroductionScreen(onLogin: () -> Unit, onRegister: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ){
            val (title, loginButton, registerButton) = createRefs()

            Text(
                text = "FitLife",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                color = purple
            )

            Button(
                onClick = onLogin,
                colors = ButtonDefaults.buttonColors(
                    containerColor = purple,
                    contentColor = white
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .constrainAs(loginButton) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(registerButton.top, margin = 8.dp)
                    }
            ) {
                Text("Iniciar Sesión")
            }

            TextButton(
                onClick = onRegister,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(registerButton) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Text("Regístrate", color = purple)
            }
        }
    }
}
