package com.dmitriivoronko.chartsta.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dmitriivoronko.chartsta.core.navigation.Bind
import com.dmitriivoronko.chartsta.core.navigation.NavCommandProvider
import com.dmitriivoronko.chartsta.design.theme.AppTheme
import com.dmitriivoronko.chartsta.feature.explore.presentation.ExploreScreenHolder
import com.dmitriivoronko.chartsta.feature.input.presentation.PointCountInputScreenHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val PointCountKey = "pointCount"

private const val PointCountInputRoute = "point-count-input"
internal const val ExploreRoute = "explore"

private const val ExploreDestination = "$ExploreRoute/{$PointCountKey}"

@AndroidEntryPoint
class AppActivity : ComponentActivity() {

    @Inject
    lateinit var navCommandProvider: NavCommandProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()

                navCommandProvider.Bind(navController)

                NavHost(
                    navController = navController,
                    startDestination = PointCountInputRoute,
                ) {
                    addPointCountInputRoute()
                    addExploreRoute()
                }
            }
        }
    }
}

private fun NavGraphBuilder.addExploreRoute() {
    composable(
        route = ExploreDestination,
        arguments = listOf(
            navArgument(
                PointCountKey
            ) {
                type = NavType.IntType
            }
        ),
    ) {
        ExploreScreenHolder()
    }
}

private fun NavGraphBuilder.addPointCountInputRoute() {
    composable(
        route = PointCountInputRoute,
    ) {
        PointCountInputScreenHolder()
    }
}