package com.example.psycareapp.injection

import com.example.psycareapp.data.ApiConfig
import com.example.psycareapp.repository.NewsRepository

object Injection {
    //tes123
    fun provideRepositoryNews(): NewsRepository {
        val apiService = ApiConfig.getApiNewsService()
        return NewsRepository.getInstance(apiService)
    }
}