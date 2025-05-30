package com.example.foodorderingapp.data.model

data class CartItem(
    val sepet_yemek_id: Int,
    val yemek_adi: String,
    val yemek_resim_adi: String,
    val yemek_fiyat: Int,
    val yemek_siparis_adet: Int,
    val kullanici_adi: String
)