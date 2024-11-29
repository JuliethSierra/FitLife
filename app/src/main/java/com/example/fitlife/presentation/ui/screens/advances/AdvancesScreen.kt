package com.example.fitlife.presentation.ui.screens.advances

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdvancesScreen() {
    val tabs = listOf("Mis avances", "Comunidad")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            Text(
                text = "Avances",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                indicator = { /* Sin indicador visual */ },
                divider = {} // Sin línea divisoria
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.body2,
                                color = if (selectedTabIndex == index) MaterialTheme.colors.primary else Color.Gray
                            )
                        }
                    )
                }
            }
            
            when (selectedTabIndex) {
                0 -> MyProgressView() // Vista "Mis avances"
                1 -> CommunityView()  // Vista "Comunidad"
            }
        }
    }
}
