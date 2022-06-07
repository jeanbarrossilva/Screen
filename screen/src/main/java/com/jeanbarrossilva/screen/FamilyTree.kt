package com.jeanbarrossilva.screen

import com.jeanbarrossilva.screen.extensions.screen.parent.family
import com.jeanbarrossilva.screen.extensions.any.orIf
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Gathers all the created [Screen]s in one place and is responsible for the overall configuration
 * of the UI components offered by the library.
 *
 * @param origins [Screen.Origin]s to be set as the top-level [Screen]s of the app, through which
 * navigation to their children may also be performed if these [Screen.Origin]s also extend
 * [Screen.Parent].
 **/
data class FamilyTree(private val origins: List<Screen.Origin>) {
    /**
     * All the [Screen]s below the given [origins] in the tree. Think of these as the [origins]'
     * families.
     *
     * @see Screen.Parent.family
     **/
    private val successors
        get() = origins.filterIsInstance<Screen.Parent>().flatMap(Screen.Parent::family)

    /** Combination of the given [origins] and their [successors]. **/
    val screens by lazy { origins + successors }

    /** [Screen.Origin] that's been set as the home [Screen]. **/
    val homeScreen by lazy {
        origins
            .filter { it.isHome }
            .orIf({ isEmpty() }) { throw HomelessException() }
            .orIf({ size > 1 }) { throw MultipleHomesException() }
            .first()
    }

    /** [MutableStateFlow] that receives the [Screen] we're currently in. **/
    internal val currentScreenFlow = MutableStateFlow<Screen>(homeScreen)

    constructor(vararg origins: Screen.Origin): this(origins.toList())

    /** Thrown when no [Screen.Origin] in [origins] has [Screen.Origin.isHome] set to `true`. **/
    inner class HomelessException:
        IllegalStateException("$this does not have an origin where `isHome` is true.")

    /**
     * Thrown when multiple [Screen.Origin] in [origins] have [Screen.Origin.isHome] set to `true`.
     **/
    inner class MultipleHomesException:
        IllegalStateException("$this has multiple origins where `isHome` is true.")

    /**
     * Thrown when a [Screen] that should not be a [Screen.Origin] is, in fact, a [Screen.Origin].
     **/
    inner class InvalidOriginException(screen: Screen.Origin):
        IllegalArgumentException("$screen cannot be an Screen.Origin.")

    init {
        checkIfNoneOfTheSuccessorsIsAnOrigin()
    }

    /**
     * Checks whether none of the [successors] extend [Screen.Origin].
     *
     * @throws InvalidOriginException If any of the [successors] are a [Screen.Origin].
     **/
    private fun checkIfNoneOfTheSuccessorsIsAnOrigin() {
        successors
            .find { successor -> successor is Screen.Origin }
            ?.let { throw InvalidOriginException(it as Screen.Origin) }
    }
}