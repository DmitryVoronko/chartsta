package com.dmitriivoronko.chartsta.design.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

internal val LocalIndents = staticCompositionLocalOf<Indents> {
    throw IllegalStateException("No Indents provided")
}

private val DarkColorScheme = darkColorScheme(
    primary = Blue,
    primaryContainer = Blue.copy(alpha = 0.54f),
    secondary = Yellow,
    secondaryContainer = Yellow.copy(alpha = 0.54f),
    tertiary = Green,
    tertiaryContainer = Green.copy(alpha = 0.54f),
)

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    primaryContainer = Blue.copy(alpha = 0.12f),
    secondary = Yellow,
    secondaryContainer = Yellow.copy(alpha = 0.12f),
    tertiary = Green,
    tertiaryContainer = Green.copy(alpha = 0.12f),
)

object AppTheme {

    val indents: Indents
        @Composable
        get() = LocalIndents.current
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalIndents provides Indents(),
    ) {
        val colorScheme = when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme                                                      -> DarkColorScheme
            else                                                           -> LightColorScheme
        }
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = colorScheme.primary.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                    darkTheme
            }
        }

        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}