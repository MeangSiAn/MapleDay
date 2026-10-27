package com.mosstis.mapleday.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MainRoute(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleStartEffect(Unit) {
        viewModel.onAction(MainContract.Action.ScreenStarted)
        onStopOrDispose {  }
    }

    MainScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}
