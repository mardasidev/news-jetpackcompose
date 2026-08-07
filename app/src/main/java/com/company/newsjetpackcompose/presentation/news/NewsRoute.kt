package com.company.newsjetpackcompose.presentation.news

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NewsRoute(
    viewModel: NewsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    NewsScreen(
        uiState = uiState,
        onCategorySelected = viewModel::onCategorySelected,
        onSourceSelected = viewModel::onSourceSelected,
        onClearSourceClick = viewModel::clearSourceSelection,
        onRetryClick = viewModel::refresh
    )
}
