package com.jeanbarrossilva.screen.extensions.array

/** Maps each non-null element of the given [Array] to its class name. **/
fun <T> Array<T>.classNames(): List<String?> {
    return this
        .filterNotNull()
        .map { it::class.simpleName }
}

/**
 * Joins the given argument to the [Array], starting at the specified index.
 *
 * e. g., `arrayOf("world").joining("hello" to 0)` returns `["hello", "world"]`.
 *
 * @param joint [Pair] with the argument and the start index.
 **/
inline infix fun <reified T> Array<T>.joining(joint: Pair<T, Int>): Array<T> {
    val (arg, startIndex) = joint
    return joining(arg, startIndex = startIndex)
}

/**
 * Joins the given [args] to the [Array], starting at [startIndex].
 *
 * e. g., `arrayOf("world").joining("hello")` returns `["hello", "world"]`.
 *
 * @param args Arguments to be joined, starting at [startIndex].
 * @param startIndex Index to which the first argument of [args] will be joined. Defaults to 0,
 * meaning that the [args] will be added to the start of the [Array].
 **/
inline fun <reified T> Array<T>.joining(vararg args: T, startIndex: Int = 0): Array<T> {
    var indexOfLastAddedOldArg = 0
    var indexOfLastAddedNewArg = 0

    return Array(size + args.size) { index ->
        val lastNewArgIndex = startIndex + args.lastIndex
        val isIndexInNewArgsRange = index in startIndex..lastNewArgIndex

        if (isIndexInNewArgsRange) {
            args[indexOfLastAddedNewArg++]
        } else {
            this[indexOfLastAddedOldArg++]
        }
    }
}