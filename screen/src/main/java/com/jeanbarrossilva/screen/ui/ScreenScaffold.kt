package com.jeanbarrossilva.screen.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.screen.FamilyTree

object ScreenScaffold {
    val FabArea = 56.dp + 16.dp * 2
}

/**
 * [Scaffold] that is configured based on the given [familyTree].
 *
 * @param familyTree [FamilyTree] through which the default [Composable]s for [topAppBar],
 * [floatingActionButton] and [bottomAppBar] will be created.
 * @param modifier [Modifier] to be applied to the underlying [Scaffold].
 * @param topAppBar [TopAppBar] to be added to the [Scaffold].
 * @param floatingActionButton [FloatingActionButton] to be added to the [Scaffold].
 * @param bottomAppBar [BottomAppBar] to be added to the [Scaffold].
 * @param content Content that's "behind" the [Scaffold].
 **/
@Composable
fun ScreenScaffold(
    familyTree: FamilyTree,
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit = {
        ScreenTopAppBar(familyTree)
    },
    floatingActionButton: @Composable () -> Unit = {
        ScreenFloatingActionButton(familyTree)
    },
    bottomAppBar: @Composable () -> Unit = {
        ScreenBottomAppBar(familyTree)
    },
    content: @Composable (padding: PaddingValues) -> Unit
) {
    Scaffold(
        modifier,
        topBar = topAppBar,
        bottomBar = bottomAppBar,
        floatingActionButton = floatingActionButton,
        content = content
    )
}