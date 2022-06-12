package com.example.psycareapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.psycareapp.data.*
import kotlin.collections.ArrayList

class PsyCareRepository(private val apiService: ApiPsyCareService){
    fun getPsikolog(): LiveData<Result<PsikologResponse>> = liveData{
        emit(Result.Loading)

        try {
            val response = apiService.getPsikolog()

            when {

                response.listPsikolog.isNullOrEmpty() -> {
                    emit(Result.Error("No data"))
                }

                response.status != "ok" ->{
                    emit(Result.Error("Error"))
                }

                else -> {
                    emit(Result.Success(response))
                }
            }

        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }

    }

    fun getDiscussions(): LiveData<Result<DiscussionsResponse>> = liveData{
        emit(Result.Loading)

        try {
            val response = apiService.getDiscussions()

            when {
                response.listDiscussions.isNullOrEmpty() -> {
                    emit(Result.Error("No data"))
                }

                response.status != "ok" ->{
                    emit(Result.Error("Error"))
                }

                else -> {
                    emit(Result.Success(response))
                }
            }

        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }

    }

    fun postDiscussions(id: String, nickname: String, description: String, email: String): LiveData<Result<PostDiscussionsResponse>> = liveData{
        emit(Result.Loading)

        try {
            val response = apiService.postDiscussions(id, nickname, description, email)

            when {
                response.status != "ok" ->{
                    emit(Result.Error(response.msg.toString()))
                }

                else -> {
                    emit(Result.Success(response))
                }
            }

        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }

    }

    fun getReply(idDiscussion: String): LiveData<Result<ReplyResponse>> = liveData{
        emit(Result.Loading)

        try {
            val response = apiService.getReply(idDiscussion)

            when {

                response.listReply.isNullOrEmpty() -> {
                    emit(Result.Error("No data"))
                }

                response.status != "ok" ->{
                    emit(Result.Error("Error"))
                }

                else -> {
                    emit(Result.Success(response))
                }
            }

        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }

    }

    fun getUser(idUser: String): LiveData<Result<UsersResponse>> = liveData{
        emit(Result.Loading)

        try {
            val response = apiService.getUsers(idUser)

            when {

                response.dataUser == null -> {
                    emit(Result.Error("No data"))
                }

                response.status != "ok" ->{
                    emit(Result.Error("Error"))
                }

                else -> {
                    emit(Result.Success(response))
                }
            }

        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }

    }

    fun addFavourite(idUser: String, idDiscussion: String): LiveData<Result<UsersResponse>> = liveData{
        emit(Result.Loading)

        try {
            val response = apiService.addFavourite(idUser, idDiscussion)

            when {
                response.dataUser == null -> {
                    emit(Result.Error("No data"))
                }

                response.status != "ok" ->{
                    emit(Result.Error("Error"))
                }

                else -> {
                    emit(Result.Success(response))
                }
            }

        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }

    }

    fun removeFavourite(idUser: String, idDiscussion: String): LiveData<Result<UsersResponse>> = liveData{
        emit(Result.Loading)

        try {
            val response = apiService.deleteFavourite(idUser, idDiscussion)

            when {
                response.dataUser == null -> {
                    emit(Result.Error("No data"))
                }

                response.status != "ok" ->{
                    emit(Result.Error("Error"))
                }

                else -> {
                    emit(Result.Success(response))
                }
            }

        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }

    }

    fun postReply(idCreator: String, nickname: String, description: String, email: String, idDiscussion: String): LiveData<Result<ReplyResponse>> = liveData{
        emit(Result.Loading)

        try {
            val response = apiService.postReply(idCreator, nickname, description, email, idDiscussion)

            when {
                response.status != "ok" ->{
                    emit(Result.Error(response.msg.toString()))
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
        private var instance: PsyCareRepository? = null

        fun getInstance(apiService: ApiPsyCareService): PsyCareRepository{
            return instance ?: synchronized(this){
                instance ?: PsyCareRepository(apiService)
            }.also {
                instance = it
            }
        }
    }
}