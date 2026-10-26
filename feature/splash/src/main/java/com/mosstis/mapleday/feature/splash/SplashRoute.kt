package com.mosstis.mapleday.feature.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SplashRoute(
    hasCharacter : () -> Unit,
    noCharacter : () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentHasCharacter by rememberUpdatedState(hasCharacter)
    val currentNoCharacter by rememberUpdatedState(noCharacter)

    LaunchedEffect(uiState.isFinished) {
        if (uiState.isFinished) {
            if(uiState.hasCharacter)
                currentHasCharacter()
            else
                currentNoCharacter()
        }
    }

    SplashScreen()
}
