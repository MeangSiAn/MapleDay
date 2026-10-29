package com.mosstis.mapleday.feature.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mosstis.mapleday.core.ui.component.MapleLoading

@Composable
fun MainRoute(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleStartEffect(Unit) {
        viewModel.onAction(MainContract.Action.ScreenStarted)
        onStopOrDispose {  }
    }

    Box(modifier = Modifier.safeDrawingPadding().fillMaxSize()) {
        MainScreen(
            uiState = uiState,
            onAction = viewModel::onAction,
        )

        // 보여줄 데이터가 아직 없을 때만 전체 로딩
        MapleLoading(
            isLoading = uiState.isLoading ||
                    (uiState.isRefreshing && uiState.today?.basic == null),
        )
    }
}
