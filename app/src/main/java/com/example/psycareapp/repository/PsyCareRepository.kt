package com.example.psycareapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.psycareapp.data.ApiPsyCareService
import com.example.psycareapp.data.DiscussionsResponse
import com.example.psycareapp.data.PostDiscussionsResponse
import com.example.psycareapp.data.PsikologResponse
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

    fun postDiscussions(id: String, nickname: String, description: String): LiveData<Result<PostDiscussionsResponse>> = liveData{
        emit(Result.Loading)

        try {
            val response = apiService.postDiscussions(id, nickname, description)

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