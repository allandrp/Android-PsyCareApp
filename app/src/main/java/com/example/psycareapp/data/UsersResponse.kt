package com.example.psycareapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsersResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("data")
	val dataUser: DataUser? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class DataUser(

	@field:SerializedName("favourite")
	val favourite: ArrayList<String>? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable
