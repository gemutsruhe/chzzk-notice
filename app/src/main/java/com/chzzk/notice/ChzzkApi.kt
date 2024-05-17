package com.chzzk.notice

import retrofit2.Call
import retrofit2.http.GET

interface ChzzkApi {
    @GET("categories")
    fun getCategoryList(): Call<List<Category>>

    @GET("streams")
    fun getStreamList(): Call<List<Stream>>
}