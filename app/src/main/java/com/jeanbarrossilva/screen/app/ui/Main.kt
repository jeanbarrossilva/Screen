package com.jeanbarrossilva.screen.app.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jeanbarrossilva.screen.FamilyTree
import com.jeanbarrossilva.screen.app.ui.screen.overview.OverviewScreen
import com.jeanbarrossilva.screen.app.ui.screen.settings.SettingsScreen
import com.jeanbarrossilva.screen.ui.ScreenNavHost
import com.jeanbarrossilva.screen.ui.ScreenScaffold
import soup.compose.material.motion.navigation.rememberMaterialMotionNavController

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun Main(
    activity: AppCompatActivity,
    modifier: Modifier = Modifier
) {
    val navController = rememberMaterialMotionNavController()
    val familyTree = FamilyTree(
        OverviewScreen({ activity }, { navController }),
        SettingsScreen({ activity }, { navController })
    )

    ScreenScaffold(
        familyTree,
        modifier
    ) { padding ->
        ScreenNavHost(
            navController,
            familyTree,
            Modifier.padding(padding)
        )
    }
}