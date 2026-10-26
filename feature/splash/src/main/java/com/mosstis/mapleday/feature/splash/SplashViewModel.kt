package com.mosstis.mapleday.feature.splash

import com.mosstis.mapleday.core.presentation.base.BaseViewModel
import com.mosstis.mapleday.domain.user.usecase.CheckMyCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkMyCharacterUseCase : CheckMyCharacterUseCase
) :
    BaseViewModel<
        SplashContract.State,
        SplashContract.Action,
        SplashContract.Effect,
    >(
        initialState = SplashContract.State(),
    ) {

    init {
        launchCatching(onError = {
            updateState { copy(isFinished = true, hasCharacter = false) }
        }) {
            val minLoading = async {
                delay(2_000.milliseconds)
            }
            val result = checkMyCharacterUseCase().first()
            minLoading.await()
            updateState {
                copy(
                    isFinished = true,
                    hasCharacter = result
                )
            }
        }
    }

    override fun handleAction(action: SplashContract.Action) = Unit
}
