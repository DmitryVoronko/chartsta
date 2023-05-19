package com.dmitriivoronko.chartsta.feature.explore.domain

import com.dmitriivoronko.chartsta.core.result.DomainResult
import com.dmitriivoronko.chartsta.feature.explore.domain.model.Error
import com.dmitriivoronko.chartsta.feature.explore.domain.model.Point

interface PointsRepository {

    suspend fun get(count: Int): DomainResult<List<Point>, Error>
}