package com.mosstis.mapleday.feature.splash

import com.mosstis.mapleday.core.presentation.contract.UiAction
import com.mosstis.mapleday.core.presentation.contract.UiEffect
import com.mosstis.mapleday.core.presentation.contract.UiState

object SplashContract {

    data class State(
        val isFinished: Boolean = false,
        val hasCharacter : Boolean = false, // 저장된 내 캐릭터 존재 여부 (있으면 메인, 없으면 계정 연결)
    ) : UiState

    sealed interface Action : UiAction

    sealed interface Effect : UiEffect {
        data object None : Effect
    }
}
