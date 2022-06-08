package com.example.psycareapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.psycareapp.data.ApiPsyCareService
import com.example.psycareapp.data.PsikologResponse
import kotlin.collections.ArrayList

class PsyCareRepository(private val apiService: ApiPsyCareService){
    fun getPsikolog(): LiveData<Result<ArrayList<PsikologResponse>>> = liveData{
        emit(Result.Loading)

        try {
            val response = apiService.getPsikolog()

            when {

                response.isNullOrEmpty() -> {
                    emit(Result.Error("No data"))
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