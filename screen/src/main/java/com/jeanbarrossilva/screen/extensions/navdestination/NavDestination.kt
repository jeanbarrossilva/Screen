package com.jeanbarrossilva.screen.extensions.navdestination

import androidx.navigation.NavDestination
import com.jeanbarrossilva.screen.Screen
import com.jeanbarrossilva.screen.extensions.screen.get

/**
 * Gets a [Screen] whose [Screen.route] matches the given [NavDestination]'s [NavDestination.route].
 *
 * @param screens [List] of [Screen]s in which a matching [Screen] will be searched for.
 **/
fun NavDestination.getMatchingScreenIn(screens: List<Screen>): Screen? {
    return route?.let {
        screens[it]
    }
}