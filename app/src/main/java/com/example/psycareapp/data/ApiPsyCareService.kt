package com.example.psycareapp.data

import retrofit2.Call
import retrofit2.http.*

interface ApiPsyCareService {

    @GET("api/discussions")
    suspend fun getDiscussions(): DiscussionsResponse

    @GET("api/users/favourite/{idUser}")
    suspend fun getFavourites(@Path("idUser") idUser: String): DiscussionsResponse

    @FormUrlEncoded
    @POST("api/users/fav/{idUser}")
    suspend fun addFavourite(
        @Path("idUser") idUser: String,
        @Field("discussionId") idDiscussion: String
    ): UsersResponse

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "api/users/fav/{idUser}", hasBody = true)
    suspend fun deleteFavourite(
        @Path("idUser") idUser: String,
        @Field("discussionId") idDiscussion: String
    ): UsersResponse

    @FormUrlEncoded
    @POST("api/discussions")
    suspend fun postDiscussions(
        @Field("id_creator") id: String,
        @Field("nickname") nickname: String,
        @Field("isi") description: String,
        @Field("email") email: String
    ): PostDiscussionsResponse

    @FormUrlEncoded
    @POST("api/users")
    fun addUserData(
        @Field("userId") id: String,
        @Field("username") username: String
    ): Call<PostDiscussionsResponse>

    @GET("api/users/{idUser}")
    suspend fun getUsers(@Path("idUser") idUser: String): UsersResponse

    @GET("api/psikolog")
    suspend fun getPsikolog(): PsikologResponse

    @GET("api/discussions/reply/{idDiscussion}")
    suspend fun getReply(@Path("idDiscussion") idDiscussion: String): ReplyResponse

    @FormUrlEncoded
    @POST("api/discussions/reply/{idDiscussion}")
    suspend fun postReply(
        @Path("idDiscussion") idDiscussion: String,
        @Field("id_creator") id: String,
        @Field("nickname") nickname: String,
        @Field("isi") description: String,
        @Field("email") email: String
    ): ReplyResponse

    @GET("api/users/histories/{userId}")
    fun getTestHistory(@Path("userId") userId:String): Call<TestHistoryResponse>

    @POST("api/predict/{userId}")
    fun postTest(
        @Path("userId") userId: String,
        @Body data: PostPredictObject
    ): Call<TestResponse>


}