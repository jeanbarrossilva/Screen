package com.jeanbarrossilva.screen.extensions.navcontroller

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.jeanbarrossilva.screen.Screen
import com.jeanbarrossilva.screen.extensions.screen.suspending
import com.jeanbarrossilva.screen.util.JsonConverter
import java.io.Serializable
import java.net.URLEncoder

private const val TAG = "NavController"

/**
 * Navigates to the given [route], launching at the top of the back stack if [screen] is a
 * [Screen.Origin].
 *
 * @param screen [Screen] that determines the value that'll be set to
 * [NavOptionsBuilder.launchSingleTop].
 * @param route Route to navigate to.
 **/
internal fun NavController.navigate(screen: Screen, route: String) {
    navigate(route) {
        launchSingleTop = screen is Screen.Origin
        Log.d(TAG, "navigate: Navigating to $route.")
    }
}

/**
 * Navigates to the given [screen].
 *
 * @param screen [Screen] to navigate to.
 **/
@PublishedApi
internal fun NavController.navigate(screen: Screen) {
    navigate(screen, screen.route)
}

/**
 * Navigates to the given [screen], passing the specified [argument].
 *
 * @param screen [Screen.Arguable] to navigate to.
 * @param argument Argument to be passed to [screen]. Defaults to [screen]'s
 * [Screen.Arguable.defaultArgument].
 **/
@PublishedApi
internal fun <T: Serializable> NavController.navigate(
    screen: Screen.Arguable<T>,
    argument: T
) {
    screen suspending {
        val argumentJson = JsonConverter serialize argument
        val urlEncodedArgumentJson = URLEncoder.encode(argumentJson)

        screen.setArgumentWithUrlEncodedJson(urlEncodedArgumentJson)
        navigate(screen)
    }
}