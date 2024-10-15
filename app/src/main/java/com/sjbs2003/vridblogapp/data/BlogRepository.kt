package com.sjbs2003.vridblogapp.data

import com.sjbs2003.vridblogapp.model.BlogPostData
import com.sjbs2003.vridblogapp.network.ApiService

interface BlogRepository {
    suspend fun getBlogData(): List<BlogPostData>
}

class NetworkBlogRepo(
    private val apiService: ApiService
): BlogRepository{
    override suspend fun getBlogData(): List<BlogPostData> = apiService.getBlogList()
}