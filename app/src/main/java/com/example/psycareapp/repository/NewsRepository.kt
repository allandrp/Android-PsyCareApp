package com.example.psycareapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.psycareapp.BuildConfig
import com.example.psycareapp.data.ApiNewsService
import com.example.psycareapp.data.NewsResponse

class NewsRepository(private val apiService: ApiNewsService){

    fun getMentalHealtNews(country: String): LiveData<Result<NewsResponse>> = liveData{
        emit(Result.Loading)

        try {
            val response = apiService.getMentalHealtNews(country, BuildConfig.TOKEN_API_NEWS, 4, "relevancy")

            when {
                response.status == "error" -> {
                    emit(Result.Error("error"))
                }

                response.articles.isNullOrEmpty() -> {
                    emit(Result.Error("No data"))
                }

                else -> {
                    emit(Result.Success(response))
                }
            }

        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }

    }

    companion object{
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(apiService: ApiNewsService): NewsRepository{
            return instance ?: synchronized(this){
                instance ?: NewsRepository(apiService)
            }.also {
                instance = it
            }
        }
    }
}