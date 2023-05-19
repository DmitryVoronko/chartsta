package com.dmitriivoronko.chartsta.core.coroutines.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DispatchersModule {

    companion object {

        @Provides
        @Singleton
        @DefaultDispatcher
        fun provideDefaultDispatcher(): CoroutineDispatcher =
            Dispatchers.Default
    }
}