package com.example.devsource.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val OrangeDark = Color(0xFFFF4500)    // Fire Red-Orange
val OrangeLight = Color(0xFFFF8C00)   // Dark Orange
val DeepOrange = Color(0xFFFF5722)    // Deep Orange
val BurntOrange = Color(0xFFE65100)   // Burnt Orange
val LightAmber = Color(0xFFFFB74D)    // Light Amber
val Amber = Color(0xFFFFA000)




private val DarkColorScheme = darkColorScheme(
    primary = DeepOrange,
    secondary = BurntOrange,
    tertiary = OrangeDark,
    primaryContainer = OrangeDark,
    secondaryContainer = OrangeLight
)

private val LightColorScheme = lightColorScheme(
    primary = OrangeLight,
    secondary = Amber,
    tertiary = LightAmber,
    primaryContainer = OrangeLight,
    secondaryContainer = OrangeDark
)



@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}