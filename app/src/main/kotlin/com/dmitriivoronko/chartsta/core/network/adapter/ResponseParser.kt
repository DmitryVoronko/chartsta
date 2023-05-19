package com.dmitriivoronko.chartsta.core.network.adapter

import logcat.LogPriority
import logcat.asLog
import logcat.logcat
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

private const val LogTag = "ResponseParser"

/**
 * Maps a [Response] to a [NetworkResponse].
 *
 *
 * @param errorConverter Retrofit provided body converter to parse the error body of the response
 * @return A subtype of [NetworkResponse] based on the response of the network request
 */
internal fun <S, E> Response<S>.asNetworkResponse(
    successType: Type,
    errorConverter: Converter<ResponseBody, E>,
): NetworkResponse<S, E> {
    return if (!isSuccessful) {
        parseUnsuccessfulResponse(this, errorConverter)
    } else {
        parseSuccessfulResponse(this, successType)
    }
}

/**
 * Maps an unsuccessful [Response] to [NetworkResponse.Failed].
 *
 * Controls Server and Network errors, other errors throw exception
 */
private fun <S, E> parseUnsuccessfulResponse(
    response: Response<S>,
    errorConverter: Converter<ResponseBody, E>,
): NetworkResponse.Failed<S, E> {
    val errorBody: ResponseBody =
        response.errorBody() ?: return NetworkResponse.ServerError(null, response)

    return try {
        NetworkResponse.ServerError(errorConverter.convert(errorBody), response)
    } catch (e: IOException) {
        logcat(LogTag, LogPriority.WARN) {
            e.asLog()
        }
        NetworkResponse.ServerError(null, response)
    }
}

/**
 * Maps a successful [Response] to [NetworkResponse]
 */
private fun <S, E> parseSuccessfulResponse(
    response: Response<S>,
    successType: Type,
): NetworkResponse<S, E> {
    val responseBody: S? = response.body()
    if (responseBody == null) {
        if (successType === Unit::class.java) {
            @Suppress("UNCHECKED_CAST")
            return NetworkResponse.Succeed<Unit, E>(Unit, response) as NetworkResponse<S, E>
        }

        return NetworkResponse.ServerError(null, response)
    }

    return NetworkResponse.Succeed(responseBody, response)
}

/**
 * Maps a [Throwable] to a [NetworkResponse].
 *
 * - If the error is [IOException], return [NetworkResponse.NetworkError].
 * - If the error is [HttpException], attempt to parse the underlying response and return the result
 */
internal fun <S, E> Throwable.asNetworkResponse(
    successType: Type,
    errorConverter: Converter<ResponseBody, E>,
): NetworkResponse<S, E> {
    return when (this) {
        is IOException   -> NetworkResponse.NetworkError(this)
        is HttpException -> {
            val response = response()
            if (response == null) {
                NetworkResponse.ServerError(null, null)
            } else {
                @Suppress("UNCHECKED_CAST")
                response.asNetworkResponse(successType, errorConverter) as NetworkResponse<S, E>
            }
        }

        else             -> throw this
    }
}