package com.sjbs2003.vridblogapp.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sjbs2003.vridblogapp.data.BlogRepository
import com.sjbs2003.vridblogapp.viewModel.BlogViewModel


enum class BlogScreen(val route: String){
    BlogList("blogList"),
    BlogDetail("blogDetail/{blogId}")
}
@Composable
fun BlogApp(blogRepository: BlogRepository) {
    val navController = rememberNavController()
    val blogViewModel: BlogViewModel = viewModel(factory = BlogViewModel.factory(blogRepository))

    NavHost(
        navController = navController,
        startDestination = BlogScreen.BlogList.route
    ) {
        composable(BlogScreen.BlogList.route){
            BlogListScreen(
                viewModel = blogViewModel,
                onBlogClick = { blogId ->
                    navController.navigate(BlogScreen.BlogDetail.route.replace("{blogId}", blogId.toString()))
                }
            )
        }
        composable(
            route = BlogScreen.BlogDetail.route,
            arguments = listOf(navArgument("blogId") { type = NavType.IntType })
        ){ backStackEntry ->
            val blogId = backStackEntry.arguments?.getInt("blogId") ?: return@composable
            BlogDetailScreen(
                blogId = blogId,
                viewModel = blogViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}