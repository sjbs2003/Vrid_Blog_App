package com.sjbs2003.vridblogapp.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sjbs2003.vridblogapp.network.ApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer {
    val blogRepository: BlogRepository
}

class DefaultContainer : AppContainer {

    private val json = Json { ignoreUnknownKeys = true }

    private val baseurl =
        "https://blog.vrid.in/wp-json/wp/v2/"

    private val httpClient = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseurl)
        .client(httpClient)
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val blogRepository: BlogRepository by lazy {
        NetworkBlogRepo(retrofitService)
    }
}
