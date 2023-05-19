package com.dmitriivoronko.chartsta.feature.explore.data.network

import java.time.ZonedDateTime

data class ErrorDto(
    val timestamp: ZonedDateTime?,
    val status: Int?,
    val error: String?,
    val message: String?,
    val path: String?,
)