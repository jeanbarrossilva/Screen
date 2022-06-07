package com.jeanbarrossilva.screen.app.ui.screen.itemdetails

import com.jeanbarrossilva.screen.app.data.Item
import com.jeanbarrossilva.screen.data.UiState

data class ItemDetailsUiState(val item: Item = Item.empty): UiState<ItemDetailsScreen> {
    override val screenClass = ItemDetailsScreen::class
    override val title = item.title
}