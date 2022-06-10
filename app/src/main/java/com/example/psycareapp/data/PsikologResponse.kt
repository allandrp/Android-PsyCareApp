package com.example.psycareapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PsikologResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("data")
	val listPsikolog: ArrayList<PsikologItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class PsikologItem(

	@field:SerializedName("psikologId")
	val psikologId: String? = null,

	@field:SerializedName("contact")
	val contact: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("expertise")
	val expertise: List<String?>? = null,

	@field:SerializedName("lat")
	val lat: String? = null,

	@field:SerializedName("long")
	val lng: String? = null
) : Parcelable
