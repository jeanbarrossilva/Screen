package com.jeanbarrossilva.screen.data

import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/** Component that's part of the UI scaffold. **/
sealed class ScaffoldComponent {
    /**
     * Represents a top app bar.
     *
     * @param actions [Action]s to be shown.
     * @param getBackgroundColor Getter for the background [Color].
     * @param getContentColor Getter for the content [Color].
     **/
    data class TopAppBar(
        val actions: List<Action> = emptyList(),
        private val getBackgroundColor: @Composable () -> Color = { MaterialTheme.colors.surface },
        private val getContentColor: @Composable () -> Color = {
            contentColorFor(getBackgroundColor())
        }
    ): ScaffoldComponent() {
        /** Shorthand for invoking [getBackgroundColor]. **/
        val backgroundColor
            @Composable get() = getBackgroundColor()

        /** Shorthand for invoking [getContentColor]. **/
        val contentColor
            @Composable get() = getContentColor()
    }

    /**
     * Represents a floating action button.
     *
     * @param icon [ImageVector] to be set as the icon.
     * @param label Short description of operation that'll be performed through [onClick].
     * @param getBackgroundColor Getter for the background [Color].
     * @param getContentColor Getter for the content [Color].
     * @param onClick Operation to be performed when the button is clicked.
     **/
    data class Fab(
        val icon: ImageVector,
        val label: String?,
        private val getBackgroundColor: @Composable () -> Color = { MaterialTheme.colors.surface },
        private val getContentColor: @Composable () -> Color = {
            contentColorFor(getBackgroundColor())
        },
        val onClick: () -> Unit
    ): ScaffoldComponent() {
        /** Shorthand for invoking [getBackgroundColor]. **/
        val backgroundColor
            @Composable get() = getBackgroundColor()

        /** Shorthand for invoking [getContentColor]. **/
        val contentColor
            @Composable get() = getContentColor()
    }

    /** Represents a bottom app bar. **/
    object BottomAppBar: ScaffoldComponent()
}