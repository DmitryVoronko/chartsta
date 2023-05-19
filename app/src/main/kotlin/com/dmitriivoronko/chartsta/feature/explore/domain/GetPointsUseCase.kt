package com.dmitriivoronko.chartsta.feature.explore.domain

import com.dmitriivoronko.chartsta.core.coroutines.di.DefaultDispatcher
import com.dmitriivoronko.chartsta.core.result.DomainResult
import com.dmitriivoronko.chartsta.core.result.map
import com.dmitriivoronko.chartsta.feature.explore.domain.model.Error
import com.dmitriivoronko.chartsta.feature.explore.domain.model.Point
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPointsUseCase @Inject constructor(
    private val pointsRepository: PointsRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(count: Int): DomainResult<List<Point>, Error> =
        withContext(defaultDispatcher) {
            pointsRepository.get(count)
                .map { it.sortedBy { point -> point.x } }
        }
}