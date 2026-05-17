package com.example.mealapp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(mealId: String, viewModel: MealViewModel, navController: NavController) {
    val meal by viewModel.selectedMeal.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(mealId) {
        viewModel.getMealById(mealId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Drink Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        meal?.let {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = it.strDrinkThumb,
                    contentDescription = it.strDrink,
                    modifier = Modifier.fillMaxWidth().height(250.dp),
                    contentScale = ContentScale.Crop
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(it.strDrink, style = MaterialTheme.typography.headlineMedium)
                    it.strCategory?.let { cat ->
                        Text("Category: $cat", style = MaterialTheme.typography.bodyMedium)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Instructions", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(it.strInstructions ?: "No instructions available.")

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            val searchQuery = Uri.encode(it.strDrink + " cocktail recipe")
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                "vnd.youtube://results?search_query=$searchQuery".toUri()
                            )
                            intent.setPackage("com.google.android.youtube")
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("▶ Watch on YouTube")
                    }
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}