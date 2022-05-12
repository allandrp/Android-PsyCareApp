package com.example.psycareapp.data

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        private val loggingInterceptorVal = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val client = OkHttpClient.Builder().addInterceptor(loggingInterceptorVal).build()

        fun getApiNewsService(): ApiNewsService{
            val retrofit = Retrofit.Builder().apply {
                baseUrl(" https://newsapi.org/v2/")
                addConverterFactory(GsonConverterFactory.create())
                client(client)
            }.build()

            return retrofit.create(ApiNewsService::class.java)
        }
    }
}