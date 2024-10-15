package com.sjbs2003.vridblogapp.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sjbs2003.vridblogapp.R
import com.sjbs2003.vridblogapp.model.BlogItemUiModel
import com.sjbs2003.vridblogapp.viewModel.BlogUiState

@Composable
fun HomeScreen(
    blogUiState: BlogUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (blogUiState) {
        is BlogUiState.Loading -> LoadingScreen(modifier = modifier.size(200.dp))

        is BlogUiState.Success ->
            BlogListScreen(
                blogs = blogUiState.blogs,
                modifier = modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                    ),
                contentPadding = contentPadding
            )

        is BlogUiState.Error -> ErrorScreen(retryAction , modifier)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}


@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = "error"
        )
        Text(stringResource(R.string.failed_to_load), modifier = modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}



@Composable
private fun BlogListScreen(
    blogs: List<BlogItemUiModel>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = blogs,
            key = { amphibian ->
                amphibian.title
            }
        ) { amphibian ->
            BlogCard(blogData = amphibian, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun BlogCard(
    blogData: BlogItemUiModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = blogData.title,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = modifier.height(8.dp))
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(blogData.imageUrl)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.blog_photo),
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxWidth()
            )
            Text(
                text = blogData.excerpt,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val sampleBlogs = listOf(
        BlogItemUiModel(
            title = "Sample Blog 1",
            imageUrl = "https://example.com/image1.jpg",
            excerpt = "This is a sample excerpt for the first blog post.",
            date = "",
            content = "",
        ),
        BlogItemUiModel(
            title = "Sample Blog 2",
            imageUrl = "https://example.com/image2.jpg",
            excerpt = "This is a sample excerpt for the second blog post.",
            date = "",
            content = "",
        )
    )
    HomeScreen(
        blogUiState = BlogUiState.Success(sampleBlogs),
        retryAction = {}
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(retryAction = {})
}

@Preview(showBackground = true)
@Composable
fun BlogCardPreview() {
    BlogCard(
        blogData = BlogItemUiModel(
            title = "Sample Blog Title",
            imageUrl = "https://example.com/image.jpg",
            excerpt = "This is a sample excerpt for the blog post. It gives a brief overview of the content.",
            date = "",
            content = "",
        )
    )
}
