package com.example.psycareapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TestHistoryResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("status")
	val status: String
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("historyId")
	val historyId: String,

	@field:SerializedName("hasil")
	val hasil: Hasil
) : Parcelable

@Parcelize
data class Hasil(

	@field:SerializedName("depresi")
	val depresi: Int,

	@field:SerializedName("stress")
	val stress: Int,

	@field:SerializedName("anxiety")
	val anxiety: Int
) : Parcelable
