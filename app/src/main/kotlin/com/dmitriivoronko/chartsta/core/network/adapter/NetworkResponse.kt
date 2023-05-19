package com.dmitriivoronko.chartsta.core.network.adapter

import okhttp3.Headers
import retrofit2.Response
import java.io.IOException

sealed interface NetworkResponse<S, E> {

    data class Succeed<S, E>(
        val body: S,
        val response: Response<*>,
    ) : NetworkResponse<S, E> {

        val code: Int
            get() = response.code()

        val headers: Headers
            get() = response.headers()
    }

    sealed interface Failed<S, E> : NetworkResponse<S, E> {

        fun <T> map(block: (error: Failed<S, E>) -> T): T {
            return block(this)
        }
    }

    data class ServerError<S, E>(
        val body: E?,
        val response: Response<*>?,
    ) : Failed<S, E> {

        val code: Int = response?.code() ?: 0
        val headers: Headers? = response?.headers()
    }

    data class NetworkError<S, E>(
        val error: IOException,
    ) : Failed<S, E>
}

typealias CompletableResponse<E> = NetworkResponse<Unit, E>