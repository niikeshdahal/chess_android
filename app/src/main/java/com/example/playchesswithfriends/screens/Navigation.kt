package com.example.playchesswithfriends.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavigationSetup() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "homeScreen") {
        composable("homeScreen") { HomeScreen(navController) }
        composable("setupScreen") { SetupScreen(navController) }
        composable("settingsScreen") { SettingsScreen(navController) }
        // Pass the time argument as a string in the form "minutes,increment"
        composable(
            "gameScreen/{time}/{selectedColor}",
            arguments = listOf(navArgument("time",) { type = NavType.StringType }, navArgument("selectedColor") {type = NavType.StringType})
        ) { backStackEntry ->
            // Default to 15 min + 10s if not passed
            val timeArg = backStackEntry.arguments?.getString("time") ?: "15,10"
            val parts = timeArg.split(",")
            val minutes = parts.getOrNull(0)?.toIntOrNull() ?: 15
            val increment = parts.getOrNull(1)?.toIntOrNull() ?: 10
            val selectedColor =  backStackEntry.arguments?.getString("selectedColor") ?: "white"
            GameScreen(navController, initialMinutes = minutes, initialIncrement = increment, selectedColor = selectedColor)
        }
    }
}
