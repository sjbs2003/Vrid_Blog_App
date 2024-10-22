package com.sjbs2003.vridblogapp.network

import com.sjbs2003.vridblogapp.model.BlogPostData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("wp-json/wp/v2/posts?per_page=10&page=1&_embed") // single end-point to fetch data from api
    suspend fun getPosts(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): List<BlogPostData>
}

    // Uses query parameters to control the number
    // of posts per page and the current page number.