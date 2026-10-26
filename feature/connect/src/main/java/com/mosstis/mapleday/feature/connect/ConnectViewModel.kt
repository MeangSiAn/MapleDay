package com.mosstis.mapleday.feature.connect

import com.mosstis.mapleday.core.presentation.base.BaseViewModel
import com.mosstis.mapleday.domain.user.usecase.GetLinkedAccountCharactersUseCase
import com.mosstis.mapleday.domain.user.usecase.LinkAccountUseCase
import com.mosstis.mapleday.domain.user.usecase.SaveMyCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConnectViewModel @Inject constructor(
    private val linkAccountUseCase : LinkAccountUseCase,
    private val getLinkedAccountCharactersUseCase : GetLinkedAccountCharactersUseCase,
    private val saveMyCharactersUseCase : SaveMyCharactersUseCase,
) :
    BaseViewModel<
        ConnectContract.State,
        ConnectContract.Action,
        ConnectContract.Effect,
    >(
        initialState = ConnectContract.State(),
    ) {

    init {
        loadLinkedAccount()
    }

    // 저장된 키가 있으면 목록까지 불러와 S05로, 없거나 실패하면 S04로
    private fun loadLinkedAccount() {
        launchCatching(
            onError = {
                updateState { copy(step = ConnectContract.Step.InputKey) }
            }
        ) {
            val characters = getLinkedAccountCharactersUseCase()
            updateState {
                if (characters == null) {
                    copy(step = ConnectContract.Step.InputKey)
                } else {
                    copy(step = ConnectContract.Step.SelectCharacter, userCharacterList = characters)
                }
            }
        }
    }

    override fun handleAction(action: ConnectContract.Action) {
        when (action) {
            ConnectContract.Action.BackClicked -> sendEffect(ConnectContract.Effect.NavigateBack)
            is ConnectContract.Action.KeyChange -> {
                updateState { copy(apiKey = action.key, isKeyError = false) }
            }
            ConnectContract.Action.OnConnect -> connectUser()
            is ConnectContract.Action.ToggleCharacter -> {
                updateState {
                    val next = if (action.ocid in selectedOcids) selectedOcids - action.ocid else selectedOcids + action.ocid
                    copy(selectedOcids = next)
                }
            }
            ConnectContract.Action.OnSaveCharacters -> saveCharacters()
        }
    }

    private fun connectUser() {
        if(currentState.isVerifying) return
        if(currentState.apiKey.isBlank()) return

        updateState { copy(isVerifying = true) }
        launchCatching(
            onError = {
                updateState { copy(isVerifying = false, isKeyError = true) }
            }
        ) {
            val result = linkAccountUseCase(currentState.apiKey)
            updateState {
                copy(
                    isVerifying = false,
                    step = ConnectContract.Step.SelectCharacter,
                    userCharacterList = result
                )
            }
        }
    }

    // S05: 고른 캐릭터를 저장하고 메인으로
    private fun saveCharacters() {
        if (currentState.isSaving) return
        val selected = currentState.userCharacterList.filter { it.ocid in currentState.selectedOcids }
        if (selected.isEmpty()) return

        updateState { copy(isSaving = true) }
        launchCatching(
            onError = {
                updateState { copy(isSaving = false) }
            }
        ) {
            saveMyCharactersUseCase(selected)
            updateState { copy(isSaving = false) }
            sendEffect(ConnectContract.Effect.NavigateToMain)
        }
    }
}
