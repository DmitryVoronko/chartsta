package com.dmitriivoronko.chartsta.feature.input.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PointCountInputScreenHolder() {
    val viewModel = hiltViewModel<PointCountInputViewModel>()

    val pointCount by viewModel.pointCount.collectAsStateWithLifecycle()

    PointCountInputScreen(
        pointCount = pointCount,
        onPointCountChange = viewModel::onPointCountInputChanged,
        onGoClick = viewModel::go,
    )
}