package com.sjbs2003.vridblogapp.data

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer {
    val blogRepository: BlogRepository
}

class DefaultContainer : AppContainer {

    private val json = Json { ignoreUnknownKeys = true }
    private val httpClient = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl()
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


}
