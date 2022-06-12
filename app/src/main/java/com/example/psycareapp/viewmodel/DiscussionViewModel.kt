package com.example.psycareapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.psycareapp.repository.PsyCareRepository

class DiscussionViewModel(private val psyCareRepository: PsyCareRepository):ViewModel() {
    fun getDiscussions() = psyCareRepository.getDiscussions()
    fun getUser(idUser: String) = psyCareRepository.getUser(idUser)
    fun addFavourite(idUser: String, idDiscussion: String) = psyCareRepository.addFavourite(idUser, idDiscussion)
    fun deleteFavourite(idUser: String, idDiscussion: String) = psyCareRepository.removeFavourite(idUser, idDiscussion)
    fun getReply(idDiscussion: String) = psyCareRepository.getReply(idDiscussion)
    fun postDiscussions(id: String, nickname: String, description: String, email: String) = psyCareRepository.postDiscussions(id, nickname, description, email)
    fun postReply(id: String, nickname: String, description: String, email: String, idDiscussion: String) = psyCareRepository.postReply(id, nickname, description, email, idDiscussion)
}