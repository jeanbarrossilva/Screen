package com.jeanbarrossilva.screen.app.ui.screen.itemdetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jeanbarrossilva.screen.Screen
import com.jeanbarrossilva.screen.app.data.Item
import com.jeanbarrossilva.screen.extensions.screen.viewModel

data class ItemDetailsScreen(override val parent: Screen.Parent): Screen.Arguable<Item>("item") {
    override val viewModel: ItemDetailsViewModel by viewModel()
    override val argumentClass = Item::class

    init {
        this
    }

    @Composable
    override fun Content(modifier: Modifier) {
        ItemDetails(
            this,
            modifier
        )
    }
}