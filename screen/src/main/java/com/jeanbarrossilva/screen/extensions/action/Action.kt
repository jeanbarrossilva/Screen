package com.jeanbarrossilva.screen.extensions.action

import com.jeanbarrossilva.screen.data.Action

/** [Action]s from the given [Collection] that have [Action.isPresent] set to `true`. **/
val Collection<Action>.presentOnes
    get() = filter { it.isPresent }