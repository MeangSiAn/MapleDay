package com.mosstis.mapleday.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType

@Composable
fun MainScreen(
    uiState: MainContract.State,
    onAction: (MainContract.Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MapleTheme.colors.ground),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = uiState.title,
            style = MapleTheme.typography.screenTitle,
            color = MapleTheme.colors.ink,
        )
    }
}

@Preview(name = "Main · 블랙")
@Composable
private fun MainScreenBlackPreview() {
    MapleTheme(theme = MapleThemeType.BLACK) {
        MainScreen(
            uiState = MainContract.State(),
            onAction = {},
        )
    }
}

@Preview(name = "Main · 베이지")
@Composable
private fun MainScreenBeigePreview() {
    MapleTheme(theme = MapleThemeType.BEIGE) {
        MainScreen(
            uiState = MainContract.State(),
            onAction = {},
        )
    }
}
