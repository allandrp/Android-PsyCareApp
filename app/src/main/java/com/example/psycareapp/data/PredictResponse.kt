package com.example.psycareapp.data

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val data: List<DataPredict>,

	@field:SerializedName("status")
	val status: String
)

data class DataPredict(

	@field:SerializedName("severity")
	val severity: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("explanation")
	val explanation: String,

	@field:SerializedName("recommendations")
	val recommendations: List<String>
)
