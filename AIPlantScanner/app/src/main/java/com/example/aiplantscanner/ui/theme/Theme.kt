package com.example.aiplantscanner.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(
    primary = Green20,
    onPrimary = Color.White,
    primaryContainer = Green80,
    onPrimaryContainer = Green10,
    secondary = OliveAccent,
    onSecondary = Color.White,
    background = SoftCream,
    onBackground = DarkGray,
    surface = Color.White,
    onSurface = DarkGray,
    error = Color(0xFFB00020),
    onError = Color.White
)

private val DarkColors = darkColorScheme(
    primary = Green40,
    onPrimary = Color.White,
    primaryContainer = Green10,
    onPrimaryContainer = Green80,
    secondary = OliveAccent,
    onSecondary = Color.White,
    background = Color(0xFF07140A),
    onBackground = Color(0xFFE6F7E9),
    surface = Color(0xFF0B1A10),
    onSurface = Color(0xFFE6F7E9),
    error = Color(0xFFCF6679),
    onError = Color.Black
)

@Composable
fun AIPlantScannerTheme(
    useDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        shapes = Shapes(
            small = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(12.dp),
            large = RoundedCornerShape(16.dp)
        ),
        content = content
    )
}
