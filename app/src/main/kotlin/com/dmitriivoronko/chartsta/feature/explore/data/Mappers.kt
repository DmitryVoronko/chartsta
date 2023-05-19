package com.dmitriivoronko.chartsta.feature.explore.data

import com.dmitriivoronko.chartsta.core.network.adapter.NetworkResponse
import com.dmitriivoronko.chartsta.core.result.DomainResult
import com.dmitriivoronko.chartsta.feature.explore.data.network.ErrorDto
import com.dmitriivoronko.chartsta.feature.explore.domain.model.Error

internal inline fun <S, R> NetworkResponse<S, ErrorDto>.map(
    mapper: (S) -> R,
): DomainResult<R, Error> = when (this) {
    is NetworkResponse.NetworkError -> {
        DomainResult.Failed(
            Error.NetworkError(
                cause = this.error,
            )
        )
    }

    is NetworkResponse.ServerError  -> {
        DomainResult.Failed(
            Error.ServerError(
                httpCode = this.code,
                timestamp = this.body?.timestamp,
                status = this.body?.status,
                error = this.body?.error,
                message = this.body?.message,
                path = this.body?.path,
            )
        )
    }

    is NetworkResponse.Succeed      -> {
        val model = mapper(this.body)
        DomainResult.Succeed(model)
    }
}