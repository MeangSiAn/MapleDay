package com.mosstis.mapleday.feature.main

import com.mosstis.mapleday.core.presentation.contract.UiAction
import com.mosstis.mapleday.core.presentation.contract.UiEffect
import com.mosstis.mapleday.core.presentation.contract.UiState
import com.mosstis.mapleday.domain.character.model.TodayOverview
import com.mosstis.mapleday.domain.user.model.AccountCharacter

object MainContract {

    enum class MainError {
        TokenError, // APP_TOKEN_INVALID 에러
        Network, // 네트워크 에러
        Unknown
    }
    data class State(
        val isLoading : Boolean = false,
        val characterList: List<AccountCharacter> = emptyList(),
        val today : TodayOverview? = null, // 대표 캐릭터 Today 데이터
        val isRefreshing : Boolean = false, // 새로고침
        val error : MainError? = null
    ) : UiState

    sealed interface Action : UiAction {
        data object ScreenStarted : Action
    }

    sealed interface Effect : UiEffect {
        data object None : Effect
    }
}
