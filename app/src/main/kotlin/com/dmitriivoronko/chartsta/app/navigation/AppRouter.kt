package com.dmitriivoronko.chartsta.app.navigation

import com.dmitriivoronko.chartsta.app.ExploreRoute
import com.dmitriivoronko.chartsta.core.navigation.NavCommandDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRouter @Inject constructor(
    private val navCommandDispatcher: NavCommandDispatcher,
) {

    suspend fun navigateToExploreScreen(
        pointCount: Int,
    ) {
        navCommandDispatcher.dispatch { navController ->
            navController.navigate("$ExploreRoute/$pointCount")
        }
    }

    suspend fun back() {
        navCommandDispatcher.dispatch { navController ->
            navController.popBackStack()
        }
    }
}