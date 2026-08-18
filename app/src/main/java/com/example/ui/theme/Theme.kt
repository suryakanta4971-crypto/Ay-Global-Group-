package com.example.ui.theme

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

private val ImmersiveDarkColorScheme = darkColorScheme(
    primary = ImmersivePrimary,
    onPrimary = Color.White,
    primaryContainer = ImmersiveSurfaceVariant,
    onPrimaryContainer = ImmersivePrimaryGlow,
    secondary = ImmersiveCyan,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF00363A),
    onSecondaryContainer = ImmersiveCyanGlow,
    tertiary = NexusEmerald,
    onTertiary = Color.Black,
    background = ImmersiveBackground,
    onBackground = ImmersiveTextPrimary,
    surface = ImmersiveSurface,
    onSurface = ImmersiveTextPrimary,
    surfaceVariant = ImmersiveSurfaceVariant,
    onSurfaceVariant = ImmersiveTextSecondary,
    outline = ImmersiveBorder,
    outlineVariant = ImmersiveBorderSubtle
)

private val ImmersiveLightColorScheme = lightColorScheme(
    primary = ImmersivePrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEEF2FF),
    onPrimaryContainer = Color(0xFF1E293B),
    secondary = Color(0xFF00838F),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F7FA),
    onSecondaryContainer = Color(0xFF006064),
    tertiary = NexusEmerald,
    onTertiary = Color.White,
    background = ImmersiveBackground, // Keep immersive dark mood as default signature
    onBackground = ImmersiveTextPrimary,
    surface = ImmersiveSurface,
    onSurface = ImmersiveTextPrimary,
    surfaceVariant = ImmersiveSurfaceVariant,
    onSurfaceVariant = ImmersiveTextSecondary,
    outline = ImmersiveBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to Immersive UI dark canvas
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) ImmersiveDarkColorScheme else ImmersiveLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
