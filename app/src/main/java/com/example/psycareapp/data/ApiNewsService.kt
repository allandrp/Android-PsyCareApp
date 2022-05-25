package com.example.psycareapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiNewsService {

    @GET("top-headlines?category=health")
    suspend fun getMentalHealtNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("pageSize") pageSize: Int,
        @Query("sortBy") sortBy: String
    ): NewsResponse

}