package com.mosstis.mapleday.core.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType

/**
 * 화면 전체를 덮는 로딩. [isLoading]이 true면 `Scrim` 위에 로딩 표시를 띄우고 뒤 화면 터치를 막는다.
 * 화면 `Box`의 마지막 자식으로 두어야 위에 그려진다.
 */
@Composable
fun MapleLoading(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {

    AnimatedVisibility(visible = isLoading, enter = fadeIn(), exit = fadeOut()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MapleTheme.colors.scrim)
                .pointerInput(Unit) { detectTapGestures { } },
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(color = MapleTheme.colors.orange, strokeWidth = 3.dp)
        }
    }
}

@Composable
private fun LoadingSample(theme: MapleThemeType) {
    MapleTheme(theme = theme) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MapleTheme.colors.ground),
        ) {
            MapleLoading(isLoading = true)
        }
    }
}

@Preview(name = "Loading · 블랙", widthDp = 360, heightDp = 640)
@Composable
private fun LoadingBlackPreview() = LoadingSample(MapleThemeType.BLACK)

@Preview(name = "Loading · 베이지", widthDp = 360, heightDp = 640)
@Composable
private fun LoadingBeigePreview() = LoadingSample(MapleThemeType.BEIGE)
