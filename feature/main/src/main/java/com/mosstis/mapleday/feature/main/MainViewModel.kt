package com.mosstis.mapleday.feature.main

import androidx.lifecycle.viewModelScope
import com.mosstis.mapleday.core.presentation.base.BaseViewModel
import com.mosstis.mapleday.domain.character.usecase.ObserveTodayUseCase
import com.mosstis.mapleday.domain.character.usecase.RefreshTodayUseCase
import com.mosstis.mapleday.domain.user.usecase.GetMyCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMyCharactersUseCase: GetMyCharactersUseCase,
    private val observeTodayUseCase: ObserveTodayUseCase,
    private val refreshTodayUseCase: RefreshTodayUseCase,
) :
    BaseViewModel<
        MainContract.State,
        MainContract.Action,
        MainContract.Effect,
    >(
        initialState = MainContract.State(isLoading = true),
    ) {

    private var refreshJob : Job? = null
    private var lastRefreshAt: Long = 0L // 마지막 성공시간
    private var refreshedOcids: Set<String> = emptySet() // 이번 실행에서 받아온 ocid

    init {
        getUserCharacterList()
    }

    private fun getUserCharacterList() {
        launchCatching(
            onError = {
                updateState {
                    copy(isLoading = false)
                }
            }
        ) {
            getMyCharactersUseCase()
                .onEach { list ->
                    updateState { copy(characterList = list) }
                    refreshIfNeeded()
                }
                .map { list -> list.firstOrNull()?.ocid } //대표 캐릭터 = 첫 번째
                .distinctUntilChanged()
                .flatMapLatest { ocid ->
                    // 대표 ocid가 바뀌면 이전 구독은 끊고 새 ocid로 구독
                    if(ocid == null) flowOf(null) else observeTodayUseCase(ocid)
                }
                .collect { overView ->
                    updateState {
                        copy(isLoading = false, today = overView)
                    }
                }
        }
    }

    private fun observeCharacterData() {

    }

    override fun handleAction(action: MainContract.Action) {
        when (action) {
            MainContract.Action.ScreenStarted -> refreshIfNeeded()
        }
    }

    // 아직 안 받은 캐릭터가 있거나, 10분 지났거나, 강제(당겨서 새로고침)면 API 재호출
    private fun refreshIfNeeded(force: Boolean = false) {
        if(refreshJob?.isActive == true) return

        val ocids = currentState.characterList.map { it.ocid }
        if(ocids.isEmpty()) return

        val hasNewCharacter = !refreshedOcids.containsAll(ocids)
        val isStale = System.currentTimeMillis() - lastRefreshAt >= REFRESH_INTERVAL_MILLIS
        if(!force && !hasNewCharacter && !isStale) return

        updateState { copy(
            isRefreshing = true
        ) }

        refreshJob = launchCatching(
            onError = {
                updateState { copy(isRefreshing = false) }
            }
        ) {
            refreshTodayUseCase(ocids)
            lastRefreshAt = System.currentTimeMillis()
            refreshedOcids = ocids.toSet()
            updateState { copy(isRefreshing = false) }
        }
    }

    private companion object {
        const val REFRESH_INTERVAL_MILLIS = 10 * 60 * 1000L   // 10분
    }
}
