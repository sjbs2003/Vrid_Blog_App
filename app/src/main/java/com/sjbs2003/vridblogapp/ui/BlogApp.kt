package com.sjbs2003.vridblogapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sjbs2003.vridblogapp.R
import com.sjbs2003.vridblogapp.viewModel.BlogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        }
    ){innerpadding -> 
        Surface(
            modifier =Modifier.fillMaxSize().padding(innerpadding),
            color = Color.DarkGray
        ) {
            val blogViewModel: BlogViewModel = viewModel(factory = BlogViewModel.factory)
            HomeScreen()
        }
    }
}