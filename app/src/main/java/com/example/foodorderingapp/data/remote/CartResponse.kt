package com.example.foodorderingapp.data.remote

import com.example.foodorderingapp.data.model.CartItem

data class CartResponse(
    val sepet_yemekler: List<CartItem>,
    val success: Int
)