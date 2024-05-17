package com.chzzk.notice

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("category")
    val name: String,
    @SerializedName("url")
    val url: String
)
