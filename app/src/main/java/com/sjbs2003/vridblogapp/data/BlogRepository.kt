package com.sjbs2003.vridblogapp.data

import com.sjbs2003.vridblogapp.model.BlogPost
import com.sjbs2003.vridblogapp.network.ApiService

interface BlogRepository {
    suspend fun getBlogData(): List<BlogPost>
}

class NetworkBlogRepo(
    private val apiService: ApiService
): BlogRepository{
    override suspend fun getBlogData(): List<BlogPost> = apiService.getBlogList()
}