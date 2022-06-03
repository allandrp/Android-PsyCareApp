package com.example.psycareapp.data

data class Discussion(
    var idCreator: String,
    var description: String,
    var reply: ArrayList<Discussion>?,
    val timestamp: Long? = null,
    var saved: Boolean
)