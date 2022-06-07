package com.jeanbarrossilva.screen.extensions.screen

import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.jeanbarrossilva.screen.Screen
import com.jeanbarrossilva.screen.interop.ScreenViewModel
import com.jeanbarrossilva.screen.interop.ScreenViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/** Whether the given [KClass] is of a [Screen.Origin]. **/
val <T: Screen> KClass<T>.isOriginClass: Boolean
    get() = this.isSubclassOf(Screen.Origin::class)

/** Shorthand for invoking [Screen.getActivity]. **/
val Screen.activity
    get() = getActivity()

/** Shorthand for invoking [Screen.getNavController]. **/
val Screen.navController
    get() = getNavController()

/**
 * Searches for a [Screen] whose [Screen.route] is the same as the given [route].
 *
 * @param route Route to search for.
 **/
operator fun List<Screen>.get(route: String): Screen? {
    return find { it.route == route }
        ?: filterIsInstance<Screen.Parent>().mapNotNull { it.searchFor(route) }.firstOrNull()
}

/**
 * Invokes the given suspending [operation].
 *
 * @param operation Operation to be performed.
 */
infix fun Screen.suspending(operation: suspend CoroutineScope.() -> Unit) {
    activity.lifecycleScope.launch(block = operation)
}

/** Gets the [ViewModel] of the given [Screen]. **/
inline fun <reified T: ViewModel> Screen.viewModel(): Lazy<T> {
    return activity.viewModels {
        viewModelFactory
    }
}

/**
 * Creates a [ScreenViewModelFactory], automatically passing the [Screen] in which this function
 * is called and the given [args].
 *
 * @param args Arguments with which the specified [ScreenViewModel] will be created.
 **/
inline fun <reified T: Screen> T.viewModelFactoryOf(vararg args: Any?): ScreenViewModelFactory<T> {
    return ScreenViewModelFactory(screen = this, *args)
}