package com.example.psycareapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.psycareapp.repository.NewsRepository

class HomeViewModel(private val newsRepository: NewsRepository): ViewModel(){
    fun getArticles() = newsRepository.getMentalHealtNews()
}