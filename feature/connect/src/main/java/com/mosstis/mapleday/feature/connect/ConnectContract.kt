package com.mosstis.mapleday.feature.connect

import com.mosstis.mapleday.core.presentation.contract.UiAction
import com.mosstis.mapleday.core.presentation.contract.UiEffect
import com.mosstis.mapleday.core.presentation.contract.UiState
import com.mosstis.mapleday.domain.user.model.AccountCharacter

/**
 * S04 계정 연결 (API 키). SPEC: 기본 / 확인 중(버튼 로딩) / 오류(입력칸 아래 한 줄) / 성공 → S05.
 */
object ConnectContract {

    enum class Step {
        Loading, // 진입 시 저장된 키 확인 중
        InputKey, // S04 계정 연결 (키 입력)
        SelectCharacter, // S05 계정 캐릭터 고르기
    }

    data class State(
        val isVerifying: Boolean = false, // 검증중 여부 (로딩)
        val apiKey: String = "", // api key
        val isKeyError : Boolean = false, // 키 입력 에러 체크
        val step : Step = Step.Loading, // ui 스텝 (저장된 키 확인 중 → S04 또는 S05)
        val userCharacterList : List<AccountCharacter> = emptyList(),
        val selectedOcids : Set<String> = emptySet(), // S05에서 선택한 캐릭터 ocid
        val isSaving : Boolean = false, // S05 캐릭터 저장 중
    ) : UiState {
        // 키가 로그·크래시 리포트에 찍히지 않도록 가린다 (S04 완료 기준)
        override fun toString(): String =
            "State(isVerifying=$isVerifying, apiKey=${if (apiKey.isEmpty()) "<empty>" else "<redacted>"})"
    }

    sealed interface Action : UiAction {
        data object BackClicked : Action
        data class KeyChange(val key : String) : Action {
            override fun toString(): String = "KeyChange(key=<redacted>)"
        }
        data object OnConnect : Action
        data class ToggleCharacter(val ocid : String) : Action
        data object OnSaveCharacters : Action
    }

    sealed interface Effect : UiEffect {
        data object NavigateBack : Effect
        data object NavigateToMain : Effect // S05 저장 완료
    }
}
