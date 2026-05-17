package com.example.mealapp

data class Meal(
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String,
    val strInstructions: String?,
    val strCategory: String?,
    val strVideo: String?
)

data class MealResponse(
    val drinks: List<Meal>?
)