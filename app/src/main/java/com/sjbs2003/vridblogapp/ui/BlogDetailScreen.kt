package com.sjbs2003.vridblogapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sjbs2003.vridblogapp.R
import com.sjbs2003.vridblogapp.model.BlogPostData
import com.sjbs2003.vridblogapp.viewModel.BlogViewModel
import com.sjbs2003.vridblogapp.data.HtmlParserUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogDetailScreen(
    blogId: Int,
    viewModel: BlogViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Blog Details", maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is BlogViewModel.BlogUiState.Loading -> LoadingScreen(modifier = Modifier.padding(innerPadding))
            is BlogViewModel.BlogUiState.Success -> {
                val blog = state.blogPosts.find { it.id == blogId }
                if (blog != null) {
                    BlogDetailContent(blog = blog, modifier = Modifier.padding(innerPadding))
                } else {
                    ErrorScreen(
                        message = "Blog not found",
                        onRetry = { viewModel.fetchBlogPosts() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
            is BlogViewModel.BlogUiState.Error -> ErrorScreen(
                message = state.message,
                onRetry = { viewModel.fetchBlogPosts() },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun BlogDetailContent(blog: BlogPostData, modifier: Modifier = Modifier) {
    val parsedContent = HtmlParserUtil.parseHtml(blog.content.rendered)

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = blog.title.rendered,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Published on: ${blog.date}",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Featured Image
        blog.featuredImageUrl?.let { imageUrl ->
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "Featured image for ${blog.title.rendered}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = parsedContent,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Categories: ${blog.categories.joinToString(", ")}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tags: ${blog.tags.joinToString(", ")}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Author ID: ${blog.author}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}