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

    /** 화면에 보여줄 오류. 예외 → 오류 매핑은 `ConnectErrorMapper.kt`. */
    enum class ConnectError {
        InvalidKey, // OPENAPI00005
        Forbidden, // OPENAPI00002 메이플스토리용 키가 아님
        NoCharacter, // 키는 유효하지만 캐릭터 0개
        Network, // 오프라인·타임아웃
        RateLimited, // 429, OPENAPI00007
        Maintenance, // OPENAPI00010, 00011
        DataPreparing, // OPENAPI00009
        Unknown,
    }

    data class State(
        val isVerifying: Boolean = false, // 검증중 여부 (로딩)
        val apiKey: String = "", // api key
        val error : ConnectError? = null, // 키 입력칸 아래 한 줄 오류 (null이면 없음)
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
