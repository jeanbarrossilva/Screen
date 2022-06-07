package com.jeanbarrossilva.screen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.jeanbarrossilva.screen.FamilyTree
import com.jeanbarrossilva.screen.extensions.composable.animation.slideInUp
import com.jeanbarrossilva.screen.extensions.composable.animation.slideOutDown

/**
 * [FloatingActionButton] that is configured based on the given [familyTree].
 *
 * @param familyTree [FamilyTree] through which the visibility will be set, alongside the icon, the
 * colors and the on click action.
 * @param modifier [Modifier] to be applied to the underlying [AnimatedVisibility].
 **/
@Composable
fun ScreenFloatingActionButton(
    familyTree: FamilyTree,
    modifier: Modifier = Modifier
) {
    val currentScreen by familyTree.currentScreenFlow.collectAsState()
    val state by currentScreen.viewModel.getUiStateFlow().collectAsState()
    val isVisible by derivedStateOf { state.fab != null }

    AnimatedVisibility(
        isVisible,
        modifier,
        enter = fadeIn(tween(durationMillis = 700)) + slideInUp(tween()),
        exit = fadeOut(tween(durationMillis = 700)) + slideOutDown(tween())
    ) {
        state.fab?.run {
            FloatingActionButton(
                onClick,
                backgroundColor = backgroundColor,
                contentColor = contentColor
            ) {
                Icon(
                    icon,
                    contentDescription = null
                )
            }
        }
    }
}