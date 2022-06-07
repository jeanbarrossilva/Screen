package com.jeanbarrossilva.screen.data

import com.jeanbarrossilva.screen.Screen
import com.jeanbarrossilva.screen.data.ScaffoldComponent.*
import com.jeanbarrossilva.screen.extensions.screen.isOriginClass
import kotlin.reflect.KClass

/** State of a [Screen] of type [T]. **/
interface UiState<T: Screen> {
    val screenClass: KClass<T>
    val title: String

    /** Top app bar configuration. Hides it if the given value is `null`. **/
    val topAppBar: TopAppBar?
        get() = TopAppBar()

    /** Floating action button configuration. Hides it if the given value is `null`. **/
    val fab: Fab?
        get() = null

    /**
     * Bottom app bar configuration; hides it if the given value is `null`. Defaults to `null` if
     * the [Screen] we're in is not a [Screen.Origin].
     **/
    val bottomAppBar
        get() = if (screenClass.isOriginClass) BottomAppBar else null
}