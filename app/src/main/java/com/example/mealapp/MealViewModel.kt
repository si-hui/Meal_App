package com.example.mealapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealViewModel : ViewModel() {

    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals: StateFlow<List<Meal>> = _meals

    private val _selectedMeal = MutableStateFlow<Meal?>(null)
    val selectedMeal: StateFlow<Meal?> = _selectedMeal

    fun searchMeals(query: String) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.searchMeals(query)
                android.util.Log.d("MEALAPP", "Result: ${result.drinks}")
                _meals.value = result.drinks ?: emptyList()
            } catch (e: Exception) {
                android.util.Log.e("MEALAPP", "Error: ${e.message}")
                e.printStackTrace()
                _meals.value = emptyList()
            }
        }
    }

    fun getMealById(id: String) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getMealById(id)
                _selectedMeal.value = result.drinks?.firstOrNull()
            } catch (e: Exception) {
                e.printStackTrace()
                _selectedMeal.value = null
            }
        }
    }

    fun searchByCategory(category: String) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getMealsByCategory(category)
                _meals.value = result.drinks ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                _meals.value = emptyList()
            }
        }
    }
}