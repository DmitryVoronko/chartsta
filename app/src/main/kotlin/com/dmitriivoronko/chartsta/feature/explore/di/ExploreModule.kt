package com.dmitriivoronko.chartsta.feature.explore.di

import com.dmitriivoronko.chartsta.feature.explore.data.PointsRepositoryImpl
import com.dmitriivoronko.chartsta.feature.explore.data.network.PointsApi
import com.dmitriivoronko.chartsta.feature.explore.data.network.PointsApiProvider
import com.dmitriivoronko.chartsta.feature.explore.domain.PointsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface ExploreModule {

    @Binds
    @ViewModelScoped
    fun bindPointsRepository(impl: PointsRepositoryImpl): PointsRepository

    companion object {

        @Provides
        @ViewModelScoped
        fun providePointApi(pointsApiProvider: PointsApiProvider): PointsApi =
            pointsApiProvider.pointsApi
    }
}