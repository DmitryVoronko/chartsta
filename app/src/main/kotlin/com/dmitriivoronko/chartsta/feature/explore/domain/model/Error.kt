package com.dmitriivoronko.chartsta.feature.explore.domain.model

import java.io.IOException
import java.time.ZonedDateTime

sealed class Error : IOException() {

    data class ServerError(
        val httpCode: Int,
        val timestamp: ZonedDateTime?,
        val status: Int?,
        val error: String?,
        override val message: String?,
        val path: String?,
    ) : Error()

    data class NetworkError(
        override val cause: IOException,
    ) : Error()
}