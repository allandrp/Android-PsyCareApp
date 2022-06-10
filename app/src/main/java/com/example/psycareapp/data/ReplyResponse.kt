package com.example.psycareapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReplyResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("data")
	val listReply: ArrayList<ReplyItem>?,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class ReplyItem(

	@field:SerializedName("id_creator")
	val idCreator: String? = null,

	@field:SerializedName("date")
	val date: Long? = null,

	@field:SerializedName("replyId")
	val replyId: String? = null,

	@field:SerializedName("nickname")
	val nickname: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("isi")
	val description: String? = null
) : Parcelable
