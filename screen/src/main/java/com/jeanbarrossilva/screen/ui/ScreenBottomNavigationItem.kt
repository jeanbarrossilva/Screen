package com.jeanbarrossilva.screen.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.jeanbarrossilva.screen.FamilyTree
import com.jeanbarrossilva.screen.Screen
import com.jeanbarrossilva.screen.extensions.navcontroller.navigate
import com.jeanbarrossilva.screen.extensions.screen.navController
import kotlinx.coroutines.launch

/**
 * [BottomNavigationItem] configured based on the given [familyTree].
 *
 * @param familyTree [FamilyTree] that determines whether the given [tab] is selected or not.
 * @param tab [Screen.Tab] to which we'll navigate to when the item is clicked. Also used to
 * configure the icon and the label of the item.
 * @param modifier [Modifier] to be applied to the underlying [BottomNavigationItem].
 **/
@Composable
fun RowScope.ScreenBottomNavigationItem(
    familyTree: FamilyTree,
    tab: Screen.Tab,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val currentScreen by familyTree.currentScreenFlow.collectAsState()

    BottomNavigationItem(
        selected = tab == currentScreen,
        onClick = {
            tab.navController.navigate(tab)
            coroutineScope.launch { familyTree.currentScreenFlow.emit(tab) }
        },
        icon = {
            Icon(
                tab.icon,
                contentDescription = null
            )
        },
        modifier,
        label = {
            Text(tab.viewModel.uiState.title)
        }
    )
}