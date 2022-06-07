package com.jeanbarrossilva.screen.test.interop

import com.jeanbarrossilva.screen.data.UiState
import com.jeanbarrossilva.screen.interop.ScreenViewModel
import com.jeanbarrossilva.screen.test.TestScreen
import com.jeanbarrossilva.screen.test.data.TestScreenUiState
import kotlin.reflect.KClass

class TestScreenViewModel(
    screen: TestScreen,
    @Suppress("UNCHECKED_CAST") override val initialUiState: TestScreenUiState<TestScreen> =
        TestScreenUiState(screen::class as KClass<TestScreen>)
): ScreenViewModel<TestScreen, UiState<TestScreen>>(screen)