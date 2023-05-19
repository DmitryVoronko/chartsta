package com.dmitriivoronko.chartsta.core.navigation.di

import com.dmitriivoronko.chartsta.core.navigation.NavCommandDispatcher
import com.dmitriivoronko.chartsta.core.navigation.NavCommandHandler
import com.dmitriivoronko.chartsta.core.navigation.NavCommandProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavModule {

    @Binds
    @Singleton
    fun bindNavCommandProvider(navCommandHandler: NavCommandHandler): NavCommandProvider

    @Binds
    @Singleton
    fun bindNavCommandDispatcher(navCommandHandler: NavCommandHandler): NavCommandDispatcher

    companion object {

        @Provides
        @Singleton
        fun provideNavCommandHandler(): NavCommandHandler =
            NavCommandHandler()
    }
}