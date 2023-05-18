package com.dmitriivoronko.chartsta.feature.input.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PointCountInputViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val pointCount = savedStateHandle.getStateFlow(PointCountKey, "")

    fun onPointCountInputChanged(
        pointCount: String,
    ) {
        savedStateHandle[PointCountKey] = pointCount
    }

    fun go() {
        //TODO navigate to explore screen
    }
}

private const val PointCountKey = "pointCount"