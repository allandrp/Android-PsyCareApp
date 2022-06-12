package com.example.psycareapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.psycareapp.repository.PsyCareRepository

class ProfileViewModel(private val psyCareRepo: PsyCareRepository): ViewModel() {
    fun getFavourites(idUser: String) = psyCareRepo.getFavourites(idUser)
}