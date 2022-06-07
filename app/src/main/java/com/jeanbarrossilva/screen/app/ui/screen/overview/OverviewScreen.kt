package com.jeanbarrossilva.screen.app.ui.screen.overview

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jeanbarrossilva.screen.Screen
import com.jeanbarrossilva.screen.app.repo.Repository
import com.jeanbarrossilva.screen.app.ui.screen.itemdetails.ItemDetailsScreen
import com.jeanbarrossilva.screen.extensions.screen.viewModel
import com.jeanbarrossilva.screen.extensions.screen.viewModelFactoryOf

data class OverviewScreen(
    override val getActivity: () -> AppCompatActivity,
    override val getNavController: () -> NavController
): Screen.Tab, Screen.Parent {
    override val viewModelFactory = viewModelFactoryOf(Repository)
    override val viewModel: OverviewViewModel by viewModel()
    override val route = "overview"
    override val isHome = true
    override val icon = Icons.Default.Home
    override val children = listOf(ItemDetailsScreen(this))

    @Composable
    override fun Content(modifier: Modifier) {
        Overview(
            this,
            modifier
        )
    }
}