package com.jeanbarrossilva.screen.app.ui.screen.overview

import com.jeanbarrossilva.screen.app.repo.Repository
import com.jeanbarrossilva.screen.interop.ScreenViewModel

class OverviewViewModel(screen: OverviewScreen, repository: Repository):
    ScreenViewModel<OverviewScreen, OverviewUiState>(screen) {
    override val initialUiState = OverviewUiState(repository.items)
}