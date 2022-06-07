package com.jeanbarrossilva.screen.extensions.composable.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.ui.unit.IntOffset

fun defaultIntOffsetSpring(): FiniteAnimationSpec<IntOffset> {
    return spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold
    )
}

fun slideInUp(animationSpec: FiniteAnimationSpec<IntOffset> = defaultIntOffsetSpring()):
    EnterTransition {
    return slideInVertically(animationSpec, initialOffsetY = { it })
}

fun slideInDown(animationSpec: FiniteAnimationSpec<IntOffset> = defaultIntOffsetSpring()):
    EnterTransition {
    return slideInVertically(animationSpec, initialOffsetY = { -it })
}

fun slideOutUp(animationSpec: FiniteAnimationSpec<IntOffset> = defaultIntOffsetSpring()):
    ExitTransition {
    return slideOutVertically(animationSpec, targetOffsetY = { -it })
}

fun slideOutDown(animationSpec: FiniteAnimationSpec<IntOffset> = defaultIntOffsetSpring()):
    ExitTransition {
    return slideOutVertically(animationSpec, targetOffsetY = { it })
}