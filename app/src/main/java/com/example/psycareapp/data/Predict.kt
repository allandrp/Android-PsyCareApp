package com.example.psycareapp.data

import com.google.gson.annotations.SerializedName

data class Predict (
    @field:SerializedName("data")
    val data: List<Int>
    )