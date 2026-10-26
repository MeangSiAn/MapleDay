package com.mosstis.mapleday.feature.main

import com.mosstis.mapleday.core.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() :
    BaseViewModel<
        MainContract.State,
        MainContract.Action,
        MainContract.Effect,
    >(
        initialState = MainContract.State(),
    ) {

    override fun handleAction(action: MainContract.Action) {
        when (action) {
            MainContract.Action.ScreenStarted -> Unit
        }
    }
}
