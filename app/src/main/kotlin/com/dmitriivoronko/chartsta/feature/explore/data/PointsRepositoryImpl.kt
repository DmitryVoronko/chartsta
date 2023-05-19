package com.dmitriivoronko.chartsta.feature.explore.data

import com.dmitriivoronko.chartsta.core.result.DomainResult
import com.dmitriivoronko.chartsta.feature.explore.data.network.PointsApi
import com.dmitriivoronko.chartsta.feature.explore.domain.PointsRepository
import com.dmitriivoronko.chartsta.feature.explore.domain.model.Error
import com.dmitriivoronko.chartsta.feature.explore.domain.model.Point
import javax.inject.Inject

class PointsRepositoryImpl @Inject constructor(
    private val pointsApi: PointsApi,
) : PointsRepository {

    override suspend fun get(count: Int): DomainResult<List<Point>, Error> =
        pointsApi.get(count)
            .map { responseDto ->
                responseDto.points.map { dto -> Point(dto.x, dto.y) }
            }
}