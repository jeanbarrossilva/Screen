package com.jeanbarrossilva.screen.extensions.screen.parent

import android.util.Log
import com.jeanbarrossilva.screen.Screen
import com.jeanbarrossilva.screen.Screen.Parent
import com.jeanbarrossilva.screen.extensions.navcontroller.navigate
import com.jeanbarrossilva.screen.extensions.screen.navController
import java.io.Serializable

private const val TAG = "Screen.Parent"

/**
 * All of the given [Parent]'s children combined with their own family if they're also [Parent]s.
 **/
val Parent.family: List<Screen>
    get() = children
        .filterIsInstance<Parent>()
        .filter { it.children.isNotEmpty() }
        .flatMap(Parent::family)
        .toMutableList()
        .apply { addAll(0, children) }
        .toList()
        .also { Log.d(TAG, "family: ${it.map(Screen::route)}") }

/** Navigates to a [Screen.Child] of type [T]. **/
inline fun <reified T: Screen.Child> Parent.navigateTo() {
    searchFor<T>()?.let {
        navController.navigate(it)
    }
}

/**
 * Navigates to a [Screen.Arguable] of type [S], passing the given [argument].
 *
 * @param argument [Serializable] argument to be passed to the [Screen.Arguable].
 **/
inline fun <reified A: Serializable, reified S: Screen.Arguable<A>> Parent.navigateTo(
    argument: A
) {
    searchFor<S>()?.let {
        navController.navigate(it, argument)
    }
}

/** @see Screen.Parent.searchFor **/
inline fun <reified T: Screen.Child> Parent.searchFor(
    noinline predicate: T.() -> Boolean = { true }
): T? {
    return searchFor(T::class, predicate)
}