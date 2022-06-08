package com.example.psycareapp.injection

import com.example.psycareapp.data.ApiConfig
import com.example.psycareapp.repository.NewsRepository
import com.example.psycareapp.repository.PsyCareRepository

object Injection {
    //tes123
    fun provideRepositoryNews(): NewsRepository {
        val apiService = ApiConfig.getApiNewsService()
        return NewsRepository.getInstance(apiService)
    }

    fun provideRepositoryPsyCare(): PsyCareRepository {
        val apiService = ApiConfig.getApiPsyCare()
        return PsyCareRepository.getInstance(apiService)
    }
}