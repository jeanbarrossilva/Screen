package com.jeanbarrossilva.screen.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun ScreenTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
    MaterialTheme(colors, content = content)
}