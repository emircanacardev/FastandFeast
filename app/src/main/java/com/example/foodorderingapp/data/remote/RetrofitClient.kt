package com.example.foodorderingapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://kasimadalan.pe.hu/") // veya Constants.BASE_URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: FoodApi by lazy {
        retrofit.create(FoodApi::class.java)
    }
}
