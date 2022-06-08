package com.example.psycareapp.data

import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.collections.ArrayList

interface ApiPsyCareService {

    @GET("psikolog")
    suspend fun getPsikolog(): ArrayList<PsikologResponse>

}