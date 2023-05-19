package com.dmitriivoronko.chartsta.core.network.di

import android.content.Context
import com.dmitriivoronko.chartsta.core.context.isDebuggableApp
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import logcat.logcat
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    companion object {

        @Provides
        @Singleton
        @BaseUrl
        fun provideBaseUrl(): String =
            "https://hr-challenge.interactivestandard.com/"

        @Provides
        @Singleton
        fun provideGson(): Gson =
            Gson()

        @Provides
        @Singleton
        fun provideBaseHttpClient(): OkHttpClient =
            OkHttpClient()

        @Provides
        @Singleton
        fun provideHttpLoggingInterceptor(
            @ApplicationContext context: Context,
        ): HttpLoggingInterceptor =
            HttpLoggingInterceptor {
                logcat { it }
            }.apply {
                if (context.isDebuggableApp) {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            }
    }
}