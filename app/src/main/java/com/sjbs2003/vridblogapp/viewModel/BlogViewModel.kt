package com.sjbs2003.vridblogapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sjbs2003.vridblogapp.data.BlogRepository
import com.sjbs2003.vridblogapp.model.BlogPostData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BlogViewModel(private val repository: BlogRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<BlogUiState>(BlogUiState.Loading)
    val uiState: StateFlow<BlogUiState> = _uiState.asStateFlow()

    init {
        fetchBlogPosts()
    }

    fun fetchBlogPosts() {
        viewModelScope.launch {
            _uiState.value = BlogUiState.Loading
            try {
                Log.d("BlogViewModel", "Fetching blog posts...")
                val blogPosts = repository.getBlogPosts()
                _uiState.value = BlogUiState.Success(blogPosts)
                Log.d("BlogViewModel", "Fetched ${blogPosts.size} blog posts")
            } catch (e: Exception) {
                Log.e("BlogViewModel", "Error fetching blog posts", e)
                _uiState.value = BlogUiState.Error("Failed to fetch blog posts: ${e.message}")
            }
        }
    }

    sealed class BlogUiState {
        object Loading : BlogUiState()
        data class Success(val blogPosts: List<BlogPostData>) : BlogUiState()
        data class Error(val message: String) : BlogUiState()
    }

    companion object {
        fun factory(
            repository: BlogRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BlogViewModel(repository) as T
            }
        }
    }
}


