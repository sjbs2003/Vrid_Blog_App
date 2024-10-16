package com.sjbs2003.vridblogapp.data

import android.util.Log
import retrofit2.HttpException
import com.sjbs2003.vridblogapp.model.BlogPostData
import com.sjbs2003.vridblogapp.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BlogRepository(private val apiService: ApiService) {
    suspend fun getBlogPosts(perPage: Int = 10, page: Int = 1): List<BlogPostData> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getPosts(perPage, page)
            } catch (e: HttpException) {
                // Handle HTTP specific errors
                when (e.code()) {
                    404 -> {
                        Log.e("BlogRepository", "Error 404: Resource not found. URL: ${e.response()?.raw()?.request?.url}")
                    }
                    500 -> {
                        Log.e("BlogRepository", "Error 500: Server error.")
                    }
                    else -> {
                        Log.e("BlogRepository", "HTTP error: ${e.code()} ${e.message()}")
                    }
                }
                emptyList() // Return empty list on error
            } catch (e: Exception) {
                Log.e("BlogRepository", "Error fetching blog posts", e)
                emptyList()
            }
        }
    }
}