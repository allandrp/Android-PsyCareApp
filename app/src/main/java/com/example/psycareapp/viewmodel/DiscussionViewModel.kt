package com.example.psycareapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.psycareapp.repository.PsyCareRepository

class DiscussionViewModel(private val psyCareRepository: PsyCareRepository):ViewModel() {
    fun getDiscussions() = psyCareRepository.getDiscussions()
    fun postDiscussions(id: String, nickname: String, description: String) = psyCareRepository.postDiscussions(id, nickname, description)
}