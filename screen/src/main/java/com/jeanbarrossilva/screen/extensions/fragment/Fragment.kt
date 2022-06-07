package com.jeanbarrossilva.screen.extensions.fragment

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

/**
 * Returns a [ComposeView] with [content] set as its content.
 *
 * @param content UI to be set as the content of the [ComposeView].
 */
fun Fragment.composeView(content: @Composable () -> Unit): ComposeView {
    return ComposeView(requireContext()).apply {
        setContent(content)
    }
}