package com.example.fitlife.presentation.ui.screens.menu

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun BottomNavigationBar(){
    BottomNavigation(
        backgroundColor = Color.LightGray,
        contentColor = Color.Black
    ) {
        val items = listOf("H", "E", "A", "P")
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Text(text = item) },
                selected = false,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview(){
    BottomNavigationBar()
}