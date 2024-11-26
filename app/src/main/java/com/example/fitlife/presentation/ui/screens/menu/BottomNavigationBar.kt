package com.example.fitlife.presentation.ui.screens.menu


import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(
            title = "Home",
            icon = Icons.Filled.Home,
            route = "InitScreen"
        ),
        BottomNavItem(
            title = "Training",
            icon = Icons.Filled.Settings,
            route = "TrainingScreen"
        ),
        BottomNavItem(
            title = "Advances",
            icon = Icons.Filled.FitnessCenter,
            route = "AdvancesScreen"
        ),
        BottomNavItem(
            title = "Profile",
            icon = Icons.Filled.Person,
            route = "ProfileScreen"
        ),
    )
    BottomNavigation(
        backgroundColor = Color(0xF0B847F8),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
