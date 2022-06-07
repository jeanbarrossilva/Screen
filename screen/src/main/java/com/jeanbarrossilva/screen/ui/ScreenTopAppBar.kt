package com.jeanbarrossilva.screen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.jeanbarrossilva.screen.FamilyTree
import com.jeanbarrossilva.screen.Screen
import com.jeanbarrossilva.screen.data.Action
import com.jeanbarrossilva.screen.extensions.action.presentOnes
import com.jeanbarrossilva.screen.extensions.composable.animation.slideInDown
import com.jeanbarrossilva.screen.extensions.composable.animation.slideOutUp
import com.jeanbarrossilva.screen.extensions.composable.composableIf

/**
 * [TopAppBar] that is configured based on the given [familyTree].
 *
 * @param familyTree [FamilyTree] through which the visibility and the title will be set, the
 * navigation icon will get shown or hidden, and the [Action]s will be displayed.
 * @param modifier [Modifier] to be applied to the underlying [TopAppBar].
 **/
@Composable
fun ScreenTopAppBar(
    familyTree: FamilyTree,
    modifier: Modifier = Modifier
) {
    val currentScreen by familyTree.currentScreenFlow.collectAsState()
    val uiState by currentScreen.viewModel.getUiStateFlow().collectAsState()
    val isVisible by derivedStateOf { uiState.topAppBar != null }

    AnimatedVisibility(
        isVisible,
        modifier,
        enter = slideInDown(tween()),
        exit = slideOutUp(tween())
    ) {
        uiState.topAppBar?.run {
            TopAppBar(
                title = { Text(uiState.title) },
                navigationIcon = composableIf(currentScreen is Screen.Child) {
                    IconButton(onClick = currentScreen::exit) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                actions = {
                    actions.presentOnes.forEach { action ->
                        action.Content()
                    }
                },
                backgroundColor = backgroundColor,
                contentColor = contentColor
            )
        }
    }
}