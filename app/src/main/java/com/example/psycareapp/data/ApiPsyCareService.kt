package com.example.psycareapp.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*
import kotlin.collections.ArrayList

interface ApiPsyCareService {

    @GET("discussions")
    suspend fun getDiscussions(): DiscussionsResponse

    @FormUrlEncoded
    @POST("discussions")
    suspend fun postDiscussions(
        @Field("id_creator") id: String,
        @Field("nickname") nickname: String,
        @Field("isi") description: String,
        @Field("email") email: String
    ): PostDiscussionsResponse

    @FormUrlEncoded
    @POST("users")
    fun addUserData(
        @Field("userId") id: String,
        @Field("username") username: String
    ): Call<PostDiscussionsResponse>

    @POST("predict/{idUser}")
    fun predictData(
        @Path("idUser") id: String,
        @Body listAnswer: Predict
    ): Call<PredictResponse>

    @GET("users/{idUser}")
    suspend fun getUsers(@Path("idUser") idUser: String): UsersResponse

    @GET("psikolog")
    suspend fun getPsikolog(): PsikologResponse

    @GET("discussions/reply/{idDiscussion}")
    suspend fun getReply(@Path("idDiscussion") idDiscussion: String): ReplyResponse

    @FormUrlEncoded
    @POST("discussions/reply/{idDiscussion}")
    suspend fun postReply(
        @Path("idDiscussion") idDiscussion: String,
        @Field("id_creator") id: String,
        @Field("nickname") nickname: String,
        @Field("isi") description: String,
        @Field("email") email: String
    ): ReplyResponse
}