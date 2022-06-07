package com.jeanbarrossilva.screen.extensions.any

/**
 * Returns the given object that it's called from if the result of [condition] is `true`; otherwise,
 * invokes the given [operation].
 *
 * @param condition Whether [operation] should be performed.
 * @param operation Operation to be performed if [condition] is `true`.
 * @return
 */
fun <T> T.orIf(condition: T.() -> Boolean, operation: T.() -> T): T {
    return if (condition()) operation() else this
}