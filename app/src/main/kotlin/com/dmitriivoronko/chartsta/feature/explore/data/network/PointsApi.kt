package com.dmitriivoronko.chartsta.feature.explore.data.network

import com.dmitriivoronko.chartsta.core.network.adapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PointsApi {

    @GET("api/test/points")
    suspend fun get(
        @Query("count") count: Int,
    ): NetworkResponse<PointResponseDto, ErrorDto>
}