package com.jeanbarrossilva.screen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jeanbarrossilva.screen.FamilyTree
import com.jeanbarrossilva.screen.Screen
import com.jeanbarrossilva.screen.extensions.composable.animation.slideInUp
import com.jeanbarrossilva.screen.extensions.composable.animation.slideOutDown

/**
 * [BottomNavigation] that is configured based on the given [familyTree].
 *
 * @param familyTree [FamilyTree] through which the visibility will be set and the [Screen.Tab]s
 * will be shown as
 * [ScreenBottomNavigationItem].
 * @param modifier [Modifier] to be applied to the underlying [AnimatedVisibility].
 * @param backgroundColor Background [Color] for the [BottomNavigation].
 * @param contentColor Content [Color] for the [BottomNavigation].
 **/
@Composable
fun ScreenBottomAppBar(
    familyTree: FamilyTree,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor)
) {
    val currentScreen by familyTree.currentScreenFlow.collectAsState()
    val state by currentScreen.viewModel.getUiStateFlow().collectAsState()
    val isVisible = state.bottomAppBar != null
    val tabs = familyTree.screens.filterIsInstance<Screen.Tab>()

    AnimatedVisibility(
        isVisible,
        modifier,
        enter = slideInUp(tween()),
        exit = slideOutDown(tween())
    ) {
        BottomNavigation(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        ) {
            tabs.forEach { tab ->
                ScreenBottomNavigationItem(
                    familyTree,
                    tab
                )
            }
        }
    }
}