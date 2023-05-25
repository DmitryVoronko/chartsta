package com.dmitriivoronko.chartsta.design.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

internal val LocalIndents = staticCompositionLocalOf<Indents> {
    throw IllegalStateException("No Indents provided")
}

private val DarkColorScheme = darkColorScheme(
    primary = Purple200,
    primaryContainer = Purple200.copy(alpha = 0.54f),
    secondary = Green500,
    secondaryContainer = Green500.copy(alpha = 0.54f),
    tertiary = Yellow300,
    tertiaryContainer = Yellow300.copy(alpha = 0.54f),
    surface = DarkBlue900,
    onSurface = Color.White,
    background = DarkBlue900,
    onBackground = Color.White,
)

object AppTheme {

    val indents: Indents
        @Composable
        get() = LocalIndents.current
}

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalIndents provides Indents(),
    ) {
        val colorScheme = DarkColorScheme
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = colorScheme.surface.toArgb()
                window.navigationBarColor = colorScheme.surface.toArgb()
                WindowCompat.getInsetsController(window, view)
                    .apply {
                        isAppearanceLightStatusBars = false
                        isAppearanceLightNavigationBars = false
                    }
            }
        }

        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}