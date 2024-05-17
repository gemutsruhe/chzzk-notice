package com.chzzk.notice

import com.google.gson.annotations.SerializedName

data class Stream(
    @SerializedName("streamer")
    val streamer: String,
    @SerializedName("listenerNum")
    val listenerNum: Int
)
