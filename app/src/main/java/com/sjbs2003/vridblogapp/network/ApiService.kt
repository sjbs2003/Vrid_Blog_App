package com.sjbs2003.vridblogapp.network

import com.sjbs2003.vridblogapp.model.BlogPostData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("wp-json/wp/v2/posts?_embed")
    suspend fun getPosts(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): List<BlogPostData>
}