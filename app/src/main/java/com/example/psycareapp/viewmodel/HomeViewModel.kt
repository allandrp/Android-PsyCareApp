package com.example.psycareapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.psycareapp.repository.NewsRepository
import com.example.psycareapp.repository.PsyCareRepository

class HomeViewModel(private val newsRepository: NewsRepository, private  val psyCareRepo: PsyCareRepository): ViewModel(){
    fun getArticles(country: String) = newsRepository.getMentalHealtNews(country)
    fun getUser(idUser: String) = psyCareRepo.getUser(idUser)
}