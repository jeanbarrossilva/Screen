package com.jeanbarrossilva.screen.app.ui.screen.itemdetails

import com.jeanbarrossilva.screen.app.data.Item
import com.jeanbarrossilva.screen.interop.ScreenViewModel

class ItemDetailsViewModel(screen: ItemDetailsScreen):
    ScreenViewModel<ItemDetailsScreen, ItemDetailsUiState>(screen) {
    override val initialUiState = ItemDetailsUiState(Item.empty)

    init {
        screen.onArgumentReceipt(::setItem)
    }

    private fun setItem(item: Item) {
        updateUiState {
            it.copy(item = item)
        }
    }
}