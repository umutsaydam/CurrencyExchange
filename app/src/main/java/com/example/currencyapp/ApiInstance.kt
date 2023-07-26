package com.example.currencyapp

import com.example.currencyapp.model.CurrencyData
import com.example.currencyapp.model.CurrencySymbolModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInstance {
    @FormUrlEncoded
    @POST("exchange")
    fun getCurrency(@Field("int") int: Int, @Field("to") to : String, @Field("base") base: String): Call<CurrencyData>

    @GET("symbols")
    fun getCurrencySymbols(): Call<CurrencySymbolModel>
}