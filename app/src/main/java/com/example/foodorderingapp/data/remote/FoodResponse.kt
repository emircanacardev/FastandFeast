package com.example.foodorderingapp.data.remote


import com.example.foodorderingapp.data.model.Food

data class FoodResponse(
    val yemekler: List<Food>,
    val success: Int
)

