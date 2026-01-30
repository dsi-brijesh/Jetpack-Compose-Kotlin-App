package com.dsi.myapplication.ui.theme

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

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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

    /*
    (!view.isInEditMode) -> Why it's used in Theme.kt

    In your theme, this check is critical because certain operations—like modifying the status bar or
    accessing the Activity window—only work on a real device.

    Prevents Crashes: Android Studio's Preview doesn't have a real "Window" or "Activity" attached to it.
    If you try to call (view.context as Activity).window during a preview without this check,
    the IDE will throw a ClassCastException and your preview will break.

    Skips Device-Specific Logic: It allows you to wrap "real-world" code (like changing system bar colors)
    so that it only runs when the app is actually installed on a phone.
    */

    val view = LocalView.current

    if (!view.isInEditMode) {

        /*
        In Jetpack Compose, a SideEffect is a specialized function used to "step outside" the
        Compose world and interact with things the UI framework doesn't control (like the Android System,
        a Bluetooth radio, or a Database).
        * */
        SideEffect {
            // This happens AFTER Compose finishes drawing the UI.
            // It tells the Android System: "Now that the UI is ready, change the bar color."
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()

            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}