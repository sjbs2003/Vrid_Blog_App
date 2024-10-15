package com.sjbs2003.vridblogapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sjbs2003.vridblogapp.BlogApplication
import com.sjbs2003.vridblogapp.data.BlogRepository
import com.sjbs2003.vridblogapp.model.BlogItemUiModel
import com.sjbs2003.vridblogapp.model.BlogPostData
import kotlinx.coroutines.launch
import java.io.IOException

sealed class BlogUiState {
    data object Loading : BlogUiState()
    data class Success(val blogs: List<BlogItemUiModel>) : BlogUiState()
    data class Error(val message: String) : BlogUiState()
}


class BlogViewModel(private val repository: BlogRepository) : ViewModel() {

    private val _uiState = MutableLiveData<BlogUiState>()
    val uiState: LiveData<BlogUiState> = _uiState

    init {
        fetchBlogPosts()
    }

    fun fetchBlogPosts() {
        _uiState.value = BlogUiState.Loading
        viewModelScope.launch {
            try {
                val blogPosts = repository.getBlogData()
                _uiState.value = BlogUiState.Success(blogPosts.map { it.toUiModel() })
            } catch (e: IOException) {
                _uiState.value = BlogUiState.Error("Network error: ${e.message}")
            } catch (e: Exception) {
                _uiState.value = BlogUiState.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }

    private fun BlogPostData.toUiModel(): BlogItemUiModel {
        return BlogItemUiModel(
            title = this.title.rendered,
            imageUrl = "", // You might need to extract this from the content or add it to your BlogPost model
            date = this.date,
            content = this.content.rendered,
            excerpt = this.excerpt.rendered
        )
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


