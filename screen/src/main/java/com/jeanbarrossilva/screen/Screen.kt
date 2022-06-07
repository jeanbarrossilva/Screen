package com.jeanbarrossilva.screen

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.jeanbarrossilva.screen.data.UiState
import com.jeanbarrossilva.screen.extensions.screen.navController
import com.jeanbarrossilva.screen.extensions.screen.parent.family
import com.jeanbarrossilva.screen.extensions.screen.parent.searchFor
import com.jeanbarrossilva.screen.extensions.screen.suspending
import com.jeanbarrossilva.screen.extensions.screen.viewModel
import com.jeanbarrossilva.screen.extensions.screen.viewModelFactoryOf
import com.jeanbarrossilva.screen.interop.ScreenViewModel
import com.jeanbarrossilva.screen.interop.ScreenViewModelFactory
import com.jeanbarrossilva.screen.util.JsonConverter
import java.io.Serializable
import java.net.URLDecoder
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull

/** Represents a screen, a destination in the app. **/
interface Screen {
    /**
     * [ViewModelProvider.Factory] to create the [ScreenViewModel] in which the [UiState] is stored.
     **/
    val viewModelFactory: ScreenViewModelFactory<*>
        get() = viewModelFactoryOf()

    /**
     * [ScreenViewModel] that'll be created by [viewModelFactory]. Can be easily gotten through
     * [Screen.viewModel].
     **/
    val viewModel: ScreenViewModel<*, *>

    /** Getter for the [AppCompatActivity] that's associated with this [Screen]. **/
    val getActivity: () -> AppCompatActivity

    /** Getter for the [NavController] that'll be used for navigating between [Screen]s. **/
    val getNavController: () -> NavController

    /** Acts as a key; represents where in the app we're at. **/
    val route: String

    /** Usually a short (one or two words) description of this [Screen]. **/
    val resource
        get() = route

    /** [List] of accepted [NamedNavArgument]s. Defaults to an empty [List]. **/
    val navArguments
        get() = emptyList<NamedNavArgument>()

    /** Whether this [Screen] has any kind of relation with the given [other]. **/
    fun isRelatedTo(other: Screen): Boolean {
        return this == other
    }

    /**
     * Exits and navigates back to this [Screen].
     *
     * @see exit
     **/
    fun reset() {
        exit()
        navController.navigate(route)
    }

    /**
     * Returns to the previous [Screen] or closes the app if this was the last one in the back
     * stack.
     **/
    fun exit() {
        navController.popBackStack()
    }

    /**
     * UI content to be shown and have the given [modifier] applied to it.
     *
     * @param modifier [Modifier] to be applied to the content.
     **/
    @Composable
    fun Content(modifier: Modifier)

    /** Top-level, [Parent]less [Screen]. **/
    interface Origin: Screen {
        /** Whether this is the [Screen] that'll get opened when the app is launched. **/
        val isHome: Boolean
            get() = false
    }

    /** [Origin] to be shown as an item of a bottom navigation component. **/
    interface Tab: Origin {
        /** [ImageVector] that represents the main purpose of this [Screen]. **/
        val icon: ImageVector
    }

    /** [Screen] that's able to have child [Screen]s. **/
    interface Parent: Screen {
        /** [Child]s belonging to this [Parent]. **/
        val children: List<Child>

        override fun isRelatedTo(other: Screen): Boolean {
            return other is Child && other isChildOf this
        }

        /**
         * Searches for a [Child] whose [Child.route] is the same as the given [route].
         *
         * @param route Route to search for.
         **/
        fun searchFor(route: String): Child? {
            return searchFor {
                this.route == route
            }
        }

        /**
         * Searches for a [Child] that matches the given [predicate].
         *
         * @param predicate Condition to match.
         **/
        fun searchFor(predicate: Child.() -> Boolean): Child? {
            return searchFor<Child>(predicate)
        }

        /**
         * Searches for a [Child] that matches the given [predicate] and whose [KClass] is the same
         * as the given [screenClass].
         *
         * @param screenClass [KClass] to search for.
         * @param predicate Condition to match.
         **/
        fun <T: Child> searchFor(screenClass: KClass<T>, predicate: T.() -> Boolean): T? {
            return children
                .filterIsInstance(screenClass.java)
                .filter { it.predicate() }
                .ifEmpty {
                    children
                        .filterIsInstance<Parent>()
                        .find { it in family }
                        ?.searchFor(screenClass, predicate)
                        ?.let(::listOf)
                        .orEmpty()
                }
                .firstOrNull()
        }
    }

    /**
     * [Screen] that belongs to a [Parent].
     *
     * @see Child.parent
     **/
    interface Child: Screen {
        override val getActivity
            get() = parent.getActivity
        override val getNavController
            get() = parent.getNavController
        override val route
            get() = "${parent.route}/$resource"
        override val resource: String

        /** The [Parent] of this [Child]. **/
        val parent: Parent

        override fun isRelatedTo(other: Screen): Boolean {
            return other == parent || parent.isRelatedTo(other)
        }

        override fun reset() {
            throw UnsupportedOperationException("A Screen.Child cannot be reset.")
        }

        /**
         * Whether this [Child] belongs to the given [parent].
         *
         * @param parent [Parent] to check if it is this [Child]'s owner.
         **/
        infix fun isChildOf(parent: Parent): Boolean {
            return parent == this.parent
        }
    }

    /**
     * [Child] that accepts a [Serializable] argument.
     *
     * @param argumentName Name to be given to the argument.
     * @param defaultArgument Value to be set as the default one for the argument.
     **/
    abstract class Arguable<T: Serializable>(
        val argumentName: String,
        val defaultArgument: T? = null
    ): Child {
        private val resourceArgumentPlaceholder = "{$argumentName}"
        private val navArgument = navArgument(argumentName) { type = NavType.StringType }
        private val argumentFlow = MutableStateFlow(defaultArgument)

        final override val getNavController
            get() = super.getNavController
        final override val navArguments
            get() = listOf(navArgument)

        override val resource = resourceArgumentPlaceholder

        /** [KClass] of [T]. **/
        abstract val argumentClass: KClass<T>

        /** Removes the argument from [navController]'s back stack entry. **/
        private fun removeArgumentFromBackStackEntry() {
            navController.currentBackStackEntry?.arguments?.remove(argumentName)
        }

        /**
         * Deserializes the given URL-encoded [json] and sets the result as the argument for this
         * [Screen.Arguable].
         **/
        internal fun setArgumentWithUrlEncodedJson(json: String) {
            suspending {
                val urlDecodedJson = URLDecoder.decode(json)
                val argument = JsonConverter.deserialize(urlDecodedJson to argumentClass)

                argumentFlow.emit(argument)
            }
        }

        override fun exit() {
            removeArgumentFromBackStackEntry()
            super.exit()
        }

        /**
         * Performs the given [operation] when an argument is received.
         *
         * @param operation Operation to be performed.
         */
        fun onArgumentReceipt(operation: (argument: T) -> Unit) {
            suspending {
                argumentFlow
                    .filterNotNull()
                    .collect { operation(it) }
            }
        }
    }
}