package com.sjbs2003.vridblogapp.network

import com.sjbs2003.vridblogapp.model.BlogPostData
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    fun getBlogList() : List<BlogPostData>
}