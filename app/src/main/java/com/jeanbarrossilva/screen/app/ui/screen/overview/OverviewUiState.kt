package com.jeanbarrossilva.screen.app.ui.screen.overview

import com.jeanbarrossilva.screen.app.data.Item
import com.jeanbarrossilva.screen.data.UiState

data class OverviewUiState(val items: List<Item> = emptyList()): UiState<OverviewScreen> {
    override val screenClass = OverviewScreen::class
    override val title = "Overview"
}