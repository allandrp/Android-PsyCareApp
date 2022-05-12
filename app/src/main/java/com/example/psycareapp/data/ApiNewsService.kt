package com.example.psycareapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiNewsService {

    @GET("everything")
    suspend fun getMentalHealtNews(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String,
        @Query("pageSize") pageSize: Int,
        @Query("sortBy") sortBy: String
    ): NewsResponse

}