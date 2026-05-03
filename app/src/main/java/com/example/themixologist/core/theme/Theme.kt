package com.example.themixologist.core.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = FabBgLight,
    onPrimary = Color.White,
    background = DetailsBgLight,
    surface = DetailsBgLight,
    onBackground = TitleColorLight,
    onSurface = TitleColorLight,
    surfaceVariant = BadgeBgLight,
    onSurfaceVariant = BadgeTextLight,
    tertiary = FavoriteActiveLight,
    outline = DividerLight,
    outlineVariant = SubtitleColorLight,
    secondary = DescColorLight
)

private val DarkColorScheme = darkColorScheme(
    primary = FabBgDark,
    onPrimary = Color.White,
    background = DetailsBgDark,
    surface = DetailsBgDark,
    onBackground = TitleColorDark,
    onSurface = TitleColorDark,
    surfaceVariant = BadgeBgDark,
    onSurfaceVariant = BadgeTextDark,
    tertiary = FavoriteActiveDark,
    outline = DividerDark,
    outlineVariant = SubtitleColorDark,
    secondary = DescColorDark
)

@Composable
fun MixologistTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
