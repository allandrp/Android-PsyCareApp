package com.example.psycareapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import kotlinx.parcelize.Parcelize

@Parcelize
class Discussion(
    val idDiscussion: String?,
    var idCreator: String?,
    var writer: String?,
    var description: String?,
    var reply: ArrayList<Discussion>?,
    val timestamp: Long? = null
):Parcelable
{
    constructor() : this(
        "",
        "",
        "",
        "",
        arrayListOf(),
        0
    )
}