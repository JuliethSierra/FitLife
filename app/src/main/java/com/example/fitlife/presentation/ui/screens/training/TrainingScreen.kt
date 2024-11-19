package com.example.fitlife.presentation.ui.screens.training

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitlife.presentation.ui.screens.menu.BottomNavigationBar

@Composable
fun TrainingScreen() {
    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .background(Color.White)) {
            Text(
                text = "Entrenamiento",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
            )

            val categories = listOf("Cardio", "Abdomen", "Pierna", "Brazo", "Full body API")
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
               items(categories){ category ->
                   TrainingCard(category)
               }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrainingPreview() {
    TrainingScreen()
}