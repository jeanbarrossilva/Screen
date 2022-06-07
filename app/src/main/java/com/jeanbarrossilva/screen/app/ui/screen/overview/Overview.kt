package com.jeanbarrossilva.screen.app.ui.screen.overview

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.screen.app.data.Item
import com.jeanbarrossilva.screen.app.ui.component.ItemCard
import com.jeanbarrossilva.screen.app.ui.screen.itemdetails.ItemDetailsScreen
import com.jeanbarrossilva.screen.app.ui.theme.ScreenTheme
import com.jeanbarrossilva.screen.extensions.screen.parent.navigateTo

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun OverviewPreview() {
    ScreenTheme {
        Overview(
            Item.samples,
            onItemClick = { }
        )
    }
}

@Composable
private fun Overview(
    items: List<Item>,
    onItemClick: (Item) -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = 8.dp

    Surface(
        modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LazyColumn(contentPadding = PaddingValues(spacing)) {
            items(items) { item ->
                val isNotLastItem = items.indexOf(item) != items.lastIndex

                ItemCard(
                    item,
                    onClick = { onItemClick(item) }
                )

                if (isNotLastItem) {
                    Spacer(Modifier.height(spacing))
                }
            }
        }
    }
}

@Composable
fun Overview(
    screen: OverviewScreen,
    modifier: Modifier = Modifier
) {
    val uiState by screen.viewModel.getUiStateFlow().collectAsState()

    Overview(
        uiState.items,
        onItemClick = { item -> screen.navigateTo<Item, ItemDetailsScreen>(item) },
        modifier
    )
}