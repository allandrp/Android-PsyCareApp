package com.example.psycareapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiscussionsResponse(

	@field:SerializedName("DiscussionsResponse")
	val discussionsResponse: List<DiscussionsResponseItem?>? = null
) : Parcelable

@Parcelize
data class DiscussionsResponseItem(

	@field:SerializedName("id_creator")
	val idCreator: String? = null,

	@field:SerializedName("discussionId")
	val discussionId: String? = null,

	@field:SerializedName("nickname")
	val nickname: String? = null,

	@field:SerializedName("isi")
	val isi: String? = null
) : Parcelable
