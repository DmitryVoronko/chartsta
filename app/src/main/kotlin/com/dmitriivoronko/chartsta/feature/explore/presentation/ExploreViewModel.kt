package com.dmitriivoronko.chartsta.feature.explore.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitriivoronko.chartsta.app.navigation.AppRouter
import com.dmitriivoronko.chartsta.core.result.doOnFailed
import com.dmitriivoronko.chartsta.core.result.getOrDefault
import com.dmitriivoronko.chartsta.feature.explore.domain.GetPointsUseCase
import com.dmitriivoronko.chartsta.feature.explore.domain.model.Error
import com.dmitriivoronko.chartsta.feature.explore.domain.model.Point
import com.dmitriivoronko.chartsta.feature.input.presentation.PointCountKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val router: AppRouter,
) : ViewModel() {

    private val _points = MutableStateFlow(emptyList<Point>())
    val points: StateFlow<List<Point>>
        get() = _points

    private val _isProgress: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isProgress: StateFlow<Boolean>
        get() = _isProgress

    private val _error: MutableStateFlow<Error?> = MutableStateFlow(null)
    val error: StateFlow<Error?>
        get() = _error

    init {
        getPoints()
    }

    private fun getPoints() {
        viewModelScope.launch {
            _error.value = null
            _isProgress.value = true
            _points.value = getPointsUseCase(requireNotNull(savedStateHandle[PointCountKey]))
                .doOnFailed { error ->
                    _error.value = error
                }
                .getOrDefault(emptyList())
            _isProgress.value = false
        }
    }

    fun retry() {
        getPoints()
    }

    fun back() {
        viewModelScope.launch {
            router.back()
        }
    }
}