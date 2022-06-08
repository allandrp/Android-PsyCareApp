package com.example.psycareapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReplyResponse(
	@field:SerializedName("ReplyResponse")
	val replyResponse: List<ReplyResponseItem?>? = null
) : Parcelable

@Parcelize
data class ReplyResponseItem(

	@field:SerializedName("id_creator")
	val idCreator: String? = null,

	@field:SerializedName("replyId")
	val replyId: String? = null,

	@field:SerializedName("nickname")
	val nickname: String? = null,

	@field:SerializedName("isi")
	val isi: String? = null
) : Parcelable
