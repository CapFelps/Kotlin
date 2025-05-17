package br.com.fiap.softekfiap.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = SoftekPurple,
    primaryVariant = SoftekDark,
    secondary = SoftekAccent
)

private val LightColorPalette = lightColors(
    primary = SoftekPurple,
    primaryVariant = SoftekDark,
    secondary = SoftekAccent
)

@Composable
fun SoftekTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = SoftekTypography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
