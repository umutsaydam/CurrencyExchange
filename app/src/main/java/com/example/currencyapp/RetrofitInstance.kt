package com.example.currencyapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.collectapi.com/economy/"
    private const val API_KEY = "7rJwTRdW5tdoPZLwZHGDPM:2Pwa6o0OozozWU6Z1MM8Qp"

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("authorization", "apikey $API_KEY")
                .addHeader("content-type", "application/json")
                .build()
            chain.proceed(request)
        }.build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getCurrency: ApiInstance by lazy {
        retrofit.create(ApiInstance::class.java)
    }
}