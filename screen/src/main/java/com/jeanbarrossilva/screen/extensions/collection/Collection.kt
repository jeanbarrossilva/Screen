package com.jeanbarrossilva.screen.extensions.collection

/**
 * Returns the index of the given [element] or null if it isn't in the [List].
 *
 * @param element Element whose index will be gotten.
 **/
fun <T> List<T>.indexOfOrNull(element: T): Int? {
    val index = indexOf(element)
    val isElementPresent = index != -1

    return if (isElementPresent) index else null
}