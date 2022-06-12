package com.example.psycareapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TestResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val data: List<DataTestResultItem>,

	@field:SerializedName("status")
	val status: String
) : Parcelable

@Parcelize
data class DataTestResultItem(

	@field:SerializedName("severity")
	val severity: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("explanation")
	val explanation: String,

	@field:SerializedName("recommendations")
	val recommendations: List<String>
) : Parcelable
