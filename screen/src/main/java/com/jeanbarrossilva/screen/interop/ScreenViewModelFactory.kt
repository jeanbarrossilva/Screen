package com.jeanbarrossilva.screen.interop

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jeanbarrossilva.screen.Screen
import com.jeanbarrossilva.screen.extensions.array.classNames
import com.jeanbarrossilva.screen.extensions.array.joining
import com.jeanbarrossilva.screen.extensions.collection.indexOfOrNull
import com.jeanbarrossilva.screen.extensions.kfunction.parameterClasses
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Injects the given [screen] as a default value for a [ViewModel] that receives a [Screen] argument
 * in its constructor.
 *
 * @param screen [Screen] to be injected.
 * @param args Arguments with which the specified [ViewModel] will be created.
 **/
class ScreenViewModelFactory<T: Screen>(private val screen: T, private vararg val args: Any?):
    ViewModelProvider.Factory {
    /** Constructor in the given [KClass] that receives a [Screen] as an argument. **/
    private val <T: Any> KClass<T>.constructorWithScreenArg
        get() = constructors.find { constructor -> screen::class in constructor.parameterClasses }

    init {
        Log.d(TAG, "init: $this")
    }

    /**
     * Creates a new instance of the given [KClass], passing the specified [args] and [screen] (if
     * applicable, at the index that it's in) as arguments.
     *
     * @param args Arguments to be passed to the selected constructor.
     **/
    private fun <T: Any> KClass<T>.callWithScreen(vararg args: Any?): T? {
        val constructor = constructorWithScreenArg ?: primaryConstructor
        val screenArgIndex = constructor?.parameterClasses?.indexOfOrNull(screen::class)
        val argsMaybeWithScreen = args withScreenAt screenArgIndex

        return constructor?.call(*argsMaybeWithScreen)
    }

    /**
     * Returns the given [Array] if the specified [index] is `null`; otherwise, joins [screen] to
     * the given [index] and returns the modified [Array].
     *
     * @param index Index to which [screen] will be joined.
     * @see [Array.joining]
     **/
    @Suppress("UNCHECKED_CAST")
    private infix fun Array<out Any?>.withScreenAt(index: Int?): Array<out Any?> {
        this as Array<Any?>
        return index?.let { joining(screen, startIndex = it) } ?: this
    }

    override fun toString(): String {
        return "ScreenViewModelFactory(screen=${screen::class.simpleName}, " +
            "args=${args.classNames()})"
    }

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return modelClass.kotlin.callWithScreen(*args)!!
    }

    companion object {
        private const val TAG = "ScreenViewModelFactory"
    }
}