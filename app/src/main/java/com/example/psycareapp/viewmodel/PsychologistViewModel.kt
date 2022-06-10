package com.example.psycareapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.psycareapp.repository.NewsRepository
import com.example.psycareapp.repository.PsyCareRepository

class PsychologistViewModel(private val psyCareRepository: PsyCareRepository): ViewModel() {
    fun getPsychologist() = psyCareRepository.getPsikolog()
}