package com.dmitriivoronko.chartsta.core.network.adapter

fun <S, E> NetworkResponse<S, E>.getOrNull(): S? = when (this) {
    is NetworkResponse.NetworkError -> null
    is NetworkResponse.ServerError  -> null
    is NetworkResponse.Succeed      -> body
}

fun <S, E> NetworkResponse<S, E>.isError(): Boolean = when (this) {
    is NetworkResponse.NetworkError -> true
    is NetworkResponse.ServerError  -> true
    is NetworkResponse.Succeed      -> false
}

fun <S1, E, S2> NetworkResponse<S1, E>.map(
    transform: (data: S1) -> S2,
): NetworkResponse<S2, E> = when (this) {
    is NetworkResponse.NetworkError -> NetworkResponse.NetworkError(error)
    is NetworkResponse.ServerError  -> NetworkResponse.ServerError(body, response)
    is NetworkResponse.Succeed      -> NetworkResponse.Succeed(transform(this.body), response)
}