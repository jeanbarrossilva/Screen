package com.jeanbarrossilva.screen.app.ui.screen.settings

import com.jeanbarrossilva.screen.interop.ScreenViewModel

class SettingsViewModel(screen: SettingsScreen):
    ScreenViewModel<SettingsScreen, SettingsUiState>(screen) {
    override val initialUiState = SettingsUiState()
}