package com.dmitriivoronko.chartsta.feature.input.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitriivoronko.chartsta.app.navigation.AppRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointCountInputViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val router: AppRouter,
) : ViewModel() {

    val pointCount = savedStateHandle.getStateFlow<Int?>(PointCountKey, null)

    fun onPointCountInputChanged(
        pointCount: Int?,
    ) {
        savedStateHandle[PointCountKey] = pointCount
    }

    fun go() {
        viewModelScope.launch {
            pointCount.value?.let { router.navigateToExploreScreen(it) }
        }
    }
}

internal const val PointCountKey = "pointCount"