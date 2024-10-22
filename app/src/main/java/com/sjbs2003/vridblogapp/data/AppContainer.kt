package com.sjbs2003.vridblogapp.data

import com.sjbs2003.vridblogapp.network.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val blogRepository: BlogRepository
}

class DefaultContainer : AppContainer {

    private val baseurl = "https://blog.vrid.in/"

    //  includes logging for debugging purposes (HttpLoggingInterceptor)
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseurl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val blogRepository: BlogRepository by lazy {
        BlogRepository(retrofitService)
    }
}