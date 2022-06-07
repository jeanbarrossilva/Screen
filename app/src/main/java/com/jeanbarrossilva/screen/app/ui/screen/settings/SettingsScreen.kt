package com.jeanbarrossilva.screen.app.ui.screen.settings

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jeanbarrossilva.screen.Screen
import com.jeanbarrossilva.screen.extensions.screen.viewModel

data class SettingsScreen(
    override val getActivity: () -> AppCompatActivity,
    override val getNavController: () -> NavController
): Screen.Tab {
    override val viewModel: SettingsViewModel by viewModel()
    override val route = "settings"
    override val icon = Icons.Default.Settings

    @Composable
    override fun Content(modifier: Modifier) {
        Settings()
    }
}