package com.mosstis.mapleday.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mosstis.mapleday.core.designsystem.component.HeroCard
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType
import com.mosstis.mapleday.core.ui.component.ExpBar
import com.mosstis.mapleday.core.ui.component.MapleLoading
import com.mosstis.mapleday.core.ui.component.MapleProgressBar
import com.mosstis.mapleday.domain.character.model.CharacterBasic
import com.mosstis.mapleday.domain.character.model.TodayOverview
import com.mosstis.mapleday.feature.main.component.MainCharacterInfoCard

@Composable
fun MainScreen(
    uiState: MainContract.State,
    onAction: (MainContract.Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MapleTheme.colors.ground),
        ) {
            // 기본 정보가 있을 때만 카드 표시 (null 처리는 화면에서)
            uiState.today?.basic?.let { basic ->
                MainCharacterInfoCard(character = basic)
            }


        }
    }
}

// Preview 전용 샘플 (NAS /app/today 실제 응답 기준)
private val PreviewState = MainContract.State(
    today = TodayOverview(
        basic = CharacterBasic(
            ocid = "e6b02412cfdb40c5186ac5a15c7676c0",
            name = "맹리엔",
            world = "크로아",
            characterClass = "렌",
            classLevel = 6,
            level = 272,
            exp = 1254292389713L,
            expRate = 22.718,
            guild = null,
            imageUrl = null, // Preview는 네트워크가 없어 이미지가 안 뜬다
            createdAt = null,
            fetchedAt = 0L,
        ),
        scheduler = null,
        todayTasks = emptyList(),
        endingSoonEvents = emptyList(),
        inspection = null,
    ),
)

@Preview(name = "Main · 블랙")
@Composable
private fun MainScreenBlackPreview() {
    MapleTheme(theme = MapleThemeType.BLACK) {
        MainScreen(
            uiState = PreviewState,
            onAction = {},
        )
    }
}

@Preview(name = "Main · 베이지")
@Composable
private fun MainScreenBeigePreview() {
    MapleTheme(theme = MapleThemeType.BEIGE) {
        MainScreen(
            uiState = PreviewState,
            onAction = {},
        )
    }
}
