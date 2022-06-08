package com.example.psycareapp.data

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("favourite")
	val favourite: List<String?>? = null,

	@field:SerializedName("username")
	val username: String? = null
)
