package com.sjbs2003.vridblogapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sjbs2003.vridblogapp.BlogApplication
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
                val blogPosts = repository.getBlogData()
                _uiState.value = BlogUiState.Success(blogPosts)
            } catch (e: Exception) {
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
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BlogApplication)
                val blogRepository = application.container.blogRepository
                BlogViewModel(repository = blogRepository)
            }
        }
    }
}


