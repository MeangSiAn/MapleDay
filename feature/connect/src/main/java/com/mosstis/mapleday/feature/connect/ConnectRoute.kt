package com.mosstis.mapleday.feature.connect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mosstis.mapleday.core.ui.component.MapleLoading

@Composable
fun ConnectRoute(
    onBack: () -> Unit,
    onFinished: () -> Unit,
    viewModel: ConnectViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentOnBack by rememberUpdatedState(onBack)
    val currentOnFinished by rememberUpdatedState(onFinished)

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                ConnectContract.Effect.NavigateBack -> currentOnBack()
                ConnectContract.Effect.NavigateToMain -> currentOnFinished()
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        ConnectScreen(
            uiState = uiState,
            onAction = viewModel::onAction,
        )
        MapleLoading(isLoading = uiState.isVerifying)
    }
}
