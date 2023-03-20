package com.ognjenlazic.medicineregister.data.api

import com.ognjenlazic.medicineregister.utilities.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private var client = OkHttpClient.Builder().apply {
        readTimeout(1000, TimeUnit.SECONDS)
        callTimeout(1000, TimeUnit.SECONDS)
        writeTimeout(1000, TimeUnit.SECONDS)
        connectTimeout(1000, TimeUnit.SECONDS)
    }.build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: Api by lazy {
        retrofit.create(Api::class.java)
    }
}