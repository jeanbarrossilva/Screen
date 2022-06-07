package com.jeanbarrossilva.screen.app.ui.screen.itemdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.screen.app.data.Item
import com.jeanbarrossilva.screen.app.ui.theme.ScreenTheme

@Composable
@Preview
private fun ItemDetailsPreview() {
    ScreenTheme {
        ItemDetails(ItemDetailsUiState(Item.sample))
    }
}

@Composable
private fun ItemDetails(
    uiState: ItemDetailsUiState,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Box(
            Modifier.fillMaxSize(),
            Alignment.Center
        ) {
            Text(uiState.item.title)
        }
    }
}

@Composable
fun ItemDetails(
    screen: ItemDetailsScreen,
    modifier: Modifier = Modifier
) {
    val uiState by screen.viewModel.getUiStateFlow().collectAsState()

    ItemDetails(
        uiState,
        modifier
    )
}