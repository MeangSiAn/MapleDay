package com.mosstis.mapleday.feature.main

import com.mosstis.mapleday.core.presentation.contract.UiAction
import com.mosstis.mapleday.core.presentation.contract.UiEffect
import com.mosstis.mapleday.core.presentation.contract.UiState

object MainContract {

    data class State(
        val title: String = "Main",
    ) : UiState

    sealed interface Action : UiAction {
        data object ScreenStarted : Action
    }

    sealed interface Effect : UiEffect {
        data object None : Effect
    }
}
