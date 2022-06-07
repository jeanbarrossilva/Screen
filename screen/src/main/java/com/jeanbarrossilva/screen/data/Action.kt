package com.jeanbarrossilva.screen.data

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Action to be displayed in an app bar.
 *
 * @param icon [ImageVector] to be used as the icon.
 * @param label Short description of what it does.
 * @param isPresent Whether it'll be present in the bar or not.
 * @param onClick Action to be performed when it is clicked.
 **/
data class Action(
    val icon: ImageVector,
    val label: String?,
    val isPresent: Boolean = true,
    val onClick: () -> Unit
) {
    /** UI content to be displayed. **/
    @Composable
    fun Content() {
        IconButton(onClick) {
            Icon(
                icon,
                contentDescription = label
            )
        }
    }
}