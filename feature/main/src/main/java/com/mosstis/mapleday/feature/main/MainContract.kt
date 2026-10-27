package com.mosstis.mapleday.feature.main

import com.mosstis.mapleday.core.presentation.contract.UiAction
import com.mosstis.mapleday.core.presentation.contract.UiEffect
import com.mosstis.mapleday.core.presentation.contract.UiState
import com.mosstis.mapleday.domain.character.model.TodayOverview
import com.mosstis.mapleday.domain.user.model.AccountCharacter

object MainContract {

    data class State(
        val isLoading : Boolean = false,
        val characterList: List<AccountCharacter> = emptyList(),
        val today : TodayOverview? = null, // 대표 캐릭터 Today 데이터
        val isRefreshing : Boolean = false, // 새로고침
    ) : UiState

    sealed interface Action : UiAction {
        data object ScreenStarted : Action
    }

    sealed interface Effect : UiEffect {
        data object None : Effect
    }
}
