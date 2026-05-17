package com.example.mealapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: MealViewModel = viewModel()

            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(navController, viewModel)
                }
                composable("results") {
                    ResultsScreen(navController, viewModel)
                }
                composable("detail/{mealId}") { backStackEntry ->
                    val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
                    DetailScreen(mealId, viewModel, navController)
                }
            }
        }
    }
}