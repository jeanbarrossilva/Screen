package com.jeanbarrossilva.screen.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.jeanbarrossilva.screen.FamilyTree
import com.jeanbarrossilva.screen.Screen
import com.jeanbarrossilva.screen.extensions.navdestination.getMatchingScreenIn
import com.jeanbarrossilva.screen.ui.ScreenNavHost.enterMotionSpec
import com.jeanbarrossilva.screen.ui.ScreenNavHost.exitMotionSpec
import kotlinx.coroutines.launch
import soup.compose.material.motion.EnterMotionSpec
import soup.compose.material.motion.ExitMotionSpec
import soup.compose.material.motion.MotionConstants
import soup.compose.material.motion.materialSharedAxisYIn
import soup.compose.material.motion.materialSharedAxisYOut
import soup.compose.material.motion.navigation.MaterialMotionNavHost
import soup.compose.material.motion.navigation.composable

@OptIn(ExperimentalAnimationApi::class)
object ScreenNavHost {
    fun enterMotionSpec(
        slideDistance: Dp = MotionConstants.DefaultSlideDistance,
        duration: Int = MotionConstants.motionDurationLong1
    ): EnterMotionSpec {
        return materialSharedAxisYIn(slideDistance, duration)
    }

    fun exitMotionSpec(
        slideDistance: Dp = MotionConstants.DefaultSlideDistance,
        duration: Int = MotionConstants.motionDurationLong1
    ): ExitMotionSpec {
        return materialSharedAxisYOut(slideDistance, duration)
    }
}

/**
 * [MaterialMotionNavHost] that is configured based on the given [familyTree].
 *
 * @param navController [NavHostController] through which navigation operations will be run.
 * @param familyTree [FamilyTree] through which the destinations will be configured.
 * @param modifier [Modifier] to be applied to the underlying [MaterialMotionNavHost].
 * @param enterMotionSpec Entrance transition.
 * @param exitMotionSpec Exiting transition.
 * @param popEnterMotionSpec Pop entrance transition.
 * @param popExitMotionSpec Pop exiting transition.
 **/
@Composable
@OptIn(ExperimentalAnimationApi::class)
@Suppress("NAME_SHADOWING")
fun ScreenNavHost(
    navController: NavHostController,
    familyTree: FamilyTree,
    modifier: Modifier = Modifier,
    screenModifier: (Screen) -> Modifier = { Modifier },
    enterMotionSpec: (previous: Screen, next: Screen) -> EnterMotionSpec = { _, _ ->
        enterMotionSpec()
    },
    exitMotionSpec: (previous: Screen, next: Screen) -> ExitMotionSpec = { _, _ ->
        exitMotionSpec()
    },
    popEnterMotionSpec: (previous: Screen, next: Screen) -> EnterMotionSpec = enterMotionSpec,
    popExitMotionSpec: (previous: Screen, next: Screen) -> ExitMotionSpec = exitMotionSpec
) {
    val coroutineScope = rememberCoroutineScope()
    val screens = familyTree.screens
    val getScreenFor = { backStackEntry: NavBackStackEntry ->
        backStackEntry.destination.getMatchingScreenIn(screens)!!
    }
    val enterMotionSpec = { previous: NavBackStackEntry, next: NavBackStackEntry ->
        enterMotionSpec(getScreenFor(previous), getScreenFor(next))
    }
    val exitMotionSpec = { previous: NavBackStackEntry, next: NavBackStackEntry ->
        exitMotionSpec(getScreenFor(previous), getScreenFor(next))
    }
    val popEnterMotionSpec = { previous: NavBackStackEntry, next: NavBackStackEntry ->
        popEnterMotionSpec(getScreenFor(previous), getScreenFor(next))
    }
    val popExitMotionSpec = { previous: NavBackStackEntry, next: NavBackStackEntry ->
        popExitMotionSpec(getScreenFor(previous), getScreenFor(next))
    }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        destination.getMatchingScreenIn(screens)?.let {
            coroutineScope.launch {
                familyTree.currentScreenFlow.emit(it)
            }
        }
    }

    MaterialMotionNavHost(
        navController,
        familyTree.homeScreen.route,
        modifier
    ) {
        screens.forEach { screen ->
            composable(
                screen.route,
                screen.navArguments,
                enterMotionSpec = enterMotionSpec,
                exitMotionSpec = exitMotionSpec,
                popEnterMotionSpec = popEnterMotionSpec,
                popExitMotionSpec = popExitMotionSpec
            ) {
                screen.Content(screenModifier(screen))
            }
        }
    }
}