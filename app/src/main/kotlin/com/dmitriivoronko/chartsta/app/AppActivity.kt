package com.dmitriivoronko.chartsta.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dmitriivoronko.chartsta.design.theme.AppTheme
import com.dmitriivoronko.chartsta.feature.input.presentation.PointCountInputScreenHolder
import dagger.hilt.android.AndroidEntryPoint

private const val PointCountInputRoute = "point-count-input"

@AndroidEntryPoint
class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = PointCountInputRoute,
                ) {
                    composable(
                        route = PointCountInputRoute,
                    ) {
                        PointCountInputScreenHolder()
                    }
                }
            }
        }
    }
}
