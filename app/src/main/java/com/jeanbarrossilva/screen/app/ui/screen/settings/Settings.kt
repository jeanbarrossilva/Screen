package com.jeanbarrossilva.screen.app.ui.screen.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun Settings(modifier: Modifier = Modifier) {
    Surface(
        modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Box(
            Modifier.fillMaxSize(),
            Alignment.Center
        ) {
            Icon(
                Icons.Default.Settings,
                contentDescription = "Settings",
                Modifier
                    .size(64.dp)
                    .alpha(ContentAlpha.medium)
            )
        }
    }
}