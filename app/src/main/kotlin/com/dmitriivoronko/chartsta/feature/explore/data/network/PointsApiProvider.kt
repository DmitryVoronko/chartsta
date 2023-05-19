package com.dmitriivoronko.chartsta.feature.explore.data.network

import com.dmitriivoronko.chartsta.core.network.adapter.NetworkResponseAdapterFactory
import com.dmitriivoronko.chartsta.core.network.di.BaseUrl
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject

class PointsApiProvider @Inject constructor(
    @BaseUrl private val baseUrl: String,
    private val gson: Gson,
    private val baseHttpClient: OkHttpClient,
    private val httpLoggingInterceptor: HttpLoggingInterceptor,
) {

    val pointsApi: PointsApi by lazy {
        val httpClient = baseHttpClient.newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(retryOnConnectionFailure = true)
            .build()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(httpClient)
            .build()
            .create()
    }
}