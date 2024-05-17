package com.chzzk.notice

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ObserveCategoryWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {

    var categoryListLiveData: MutableLiveData<List<Category>> = MutableLiveData()

    override fun doWork(): Result {
        val retrofit = RetrofitClient.retrofit;
        val api = retrofit.create(ChzzkApi::class.java)
        var outputData = Data.Builder().build()

        api.getCategoryList()
            .enqueue(object : Callback<List<Category>> {
                @SuppressLint("RestrictedApi")
                override fun onResponse(p0: Call<List<Category>>, p1: Response<List<Category>>) {
                    val categoryList = p1.body()

                    if (categoryList != null) {
                        categoryListLiveData.postValue(categoryList)
                        outputData = Data.Builder()
                            .put("categoryList", categoryListLiveData)
                            .build()
                    }
                }

                override fun onFailure(p0: Call<List<Category>>, p1: Throwable) {

                    Log.e("TEST","Fail" + p1.message)
                }

            })

        return Result.success(outputData)
    }
}