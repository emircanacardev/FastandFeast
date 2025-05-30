package com.example.foodorderingapp.data.model
import java.io.Serializable

data class Food(
    val yemek_id: String,
    val yemek_adi: String,
    val yemek_resim_adi: String,
    val yemek_fiyat: String
): Serializable