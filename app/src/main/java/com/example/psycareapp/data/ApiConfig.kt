package com.example.psycareapp.data

import androidx.viewbinding.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        private val loggingInterceptorVal = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        private val client = OkHttpClient.Builder().addInterceptor(loggingInterceptorVal).build()

        private val gson = GsonBuilder()
            .setLenient()
            .create()

        fun getApiNewsService(): ApiNewsService{
            val retrofit = Retrofit.Builder().apply {
                baseUrl("https://newsapi.org/v2/")
                addConverterFactory(GsonConverterFactory.create())
                client(client)
            }.build()

            return retrofit.create(ApiNewsService::class.java)
        }

        fun getApiPsyCare(): ApiPsyCareService{
            val retrofit = Retrofit.Builder().apply {
                baseUrl("https://asia-southeast2-psycare-app-bangkit.cloudfunctions.net/app/")
                addConverterFactory(GsonConverterFactory.create(gson))
                client(client)
            }.build()

            return retrofit.create(ApiPsyCareService::class.java)
        }
    }
}