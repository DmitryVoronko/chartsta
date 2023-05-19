package com.dmitriivoronko.chartsta.feature.explore.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ExploreScreenHolder() {
    val viewModel = hiltViewModel<ExploreViewModel>()
    val isProgress by viewModel.isProgress.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val points by viewModel.points.collectAsStateWithLifecycle()

    ExploreScreen(
        isProgress = isProgress,
        error = error,
        points = points,
        onRetryClick = viewModel::retry,
    )
}