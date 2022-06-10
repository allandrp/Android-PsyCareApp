package com.example.psycareapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiscussionsResponse(
	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("data")
	val listDiscussions: ArrayList<DiscussionItem>,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class DiscussionItem(

	@field:SerializedName("id_creator")
	val idCreator: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("discussionId")
	val discussionId: String,

	@field:SerializedName("nickname")
	val nickname: String,

	@field:SerializedName("isi")
	val isi: String,

	@field:SerializedName("date")
	val date: Long
) : Parcelable

data class PostDiscussionsResponse(
	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("data")
	val data: ArrayList<DiscussionItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)


