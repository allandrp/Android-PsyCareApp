package com.example.psycareapp.data

import retrofit2.http.*
import kotlin.collections.ArrayList

interface ApiPsyCareService {

    @GET("discussions")
    suspend fun getDiscussions(): DiscussionsResponse

    @GET("psikolog")
    suspend fun getPsikolog(): PsikologResponse

    @FormUrlEncoded
    @POST("discussions")
    suspend fun postDiscussions(
        @Field("id_creator") id: String,
        @Field("nickname") nickname: String,
        @Field("isi") description: String
    ): PostDiscussionsResponse

}