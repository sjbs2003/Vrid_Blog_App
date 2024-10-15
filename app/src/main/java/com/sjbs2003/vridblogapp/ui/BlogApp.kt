package com.sjbs2003.vridblogapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sjbs2003.vridblogapp.viewModel.BlogViewModel


enum class BlogScreen(val route: String){
    BlogList("blogList"),
    BlogDetail("blogDetail/{blogId}");

    fun createRoute(blogId: Int? = null): String {
        return when (this) {
            BlogList -> route
            BlogDetail -> route.replace("{blogId}", blogId.toString())
        }
    }
}
@Composable
fun BlogApp(
    blogViewModel: BlogViewModel = viewModel(factory = BlogViewModel.factory)
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = BlogScreen.BlogList.route
    ) {
        composable(BlogScreen.BlogList.route){
            BlogListScreen(
                viewModel = blogViewModel,
                onBlogClick = { blogId ->
                    navController.navigate(BlogScreen.BlogDetail.createRoute(blogId))
                }
            )
        }
        composable(
            route = BlogScreen.BlogDetail.route,
            arguments = listOf(navArgument("blogId") { type = NavType.IntType })
        ){ backStackEntry ->
            val blogId = backStackEntry.arguments?.getInt("blogId") ?: return@composable
            val uiState by blogViewModel.uiState.collectAsState()

            when(val state = uiState){
                is BlogViewModel.BlogUiState.Success -> {
                    val blog = state.blogPosts.find { it.id == blogId }
                    if (blog != null){
                        BlogDetailScreen(
                            blog = blog,
                            onBackClick = { navController.popBackStack() }
                        )
                    } else ErrorScreen(message = "Blog not found!")
                }
                is BlogViewModel.BlogUiState.Loading -> LoadingScreen()
                is BlogViewModel.BlogUiState.Error -> ErrorScreen(message = state.message)
            }
        }
    }
}