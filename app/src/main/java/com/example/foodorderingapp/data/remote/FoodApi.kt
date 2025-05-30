package com.example.foodorderingapp.data.remote

import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Field

interface FoodApi {
    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun getAllFoods(): FoodResponse

    @FormUrlEncoded
    @POST("yemekler/sepeteYemekEkle.php")
    suspend fun addToCart(
        @Field("yemek_adi") yemekAdi: String,
        @Field("yemek_resim_adi") yemekResimAdi: String,
        @Field("yemek_fiyat") yemekFiyat: Int,
        @Field("yemek_siparis_adet") yemekAdet: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ): Response<Void>

    @FormUrlEncoded
    @POST("yemekler/sepettekiYemekleriGetir.php")
    suspend fun getCart(
        @Field("kullanici_adi") kullaniciAdi: String
    ): CartResponse

    @FormUrlEncoded
    @POST("yemekler/sepettenYemekSil.php")
    suspend fun deleteCartItem(
        @Field("sepet_yemek_id") sepetYemekId: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ): Response<Void>
}
