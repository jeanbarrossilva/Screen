package com.jeanbarrossilva.screen.app.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.screen.app.data.Item
import com.jeanbarrossilva.screen.app.ui.theme.ScreenTheme

@Composable
@Preview
private fun ItemCardPreview() {
    ScreenTheme {
        ItemCard(
            Item.sample,
            onClick = { }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun ItemCard(
    item: Item,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick,
        modifier.fillMaxWidth()
    ) {
        Column(modifier.padding(16.dp)) {
            Text(
                item.title,
                fontWeight = FontWeight.Bold
            )

            Text(
                item.title,
                Modifier.alpha(ContentAlpha.medium)
            )
        }
    }
}