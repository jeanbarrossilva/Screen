package com.jeanbarrossilva.screen.test.data

import com.jeanbarrossilva.screen.test.TestScreen
import com.jeanbarrossilva.screen.data.UiState
import kotlin.reflect.KClass

data class TestScreenUiState<T: TestScreen>(
    override val screenClass: KClass<T>,
    override val title: String = ""
): UiState<T>