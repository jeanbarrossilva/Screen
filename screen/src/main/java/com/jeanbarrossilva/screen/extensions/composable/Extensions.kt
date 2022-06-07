package com.jeanbarrossilva.screen.extensions.composable

import androidx.compose.runtime.Composable

/**
 * Returns the given [content] if [condition] is `true`; otherwise, `null`.
 *
 * @param condition Condition to determine whether [content] will be returned or not.
 * @param content [Composable] content to be returned depending on [condition].
 **/
fun composableIf(condition: Boolean, content: @Composable () -> Unit): @Composable (() -> Unit)? {
    return if (condition) @Composable { content } else null
}