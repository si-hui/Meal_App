package com.example.mealapp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

@Composable
fun HomeScreen(navController: NavController, viewModel: MealViewModel) {
    var query by remember { mutableStateOf("") }

    val categories = listOf("Cocktail", "Shot", "Beer", "Mocktail", "Shake", "Coffee / Tea")


    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🍹 DrinkSearch", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(24.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { category ->
                AssistChip(
                    onClick = {
                        viewModel.searchByCategory(category)
                        navController.navigate("results")
                    },
                    label = { Text(category) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search recipes...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.searchMeals(query)
                navController.navigate("results")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }
    }
}