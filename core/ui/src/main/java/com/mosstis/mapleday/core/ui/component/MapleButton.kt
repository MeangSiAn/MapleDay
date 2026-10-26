package com.mosstis.mapleday.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mosstis.mapleday.core.designsystem.theme.MapleColors
import com.mosstis.mapleday.core.designsystem.theme.MapleDimens
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType

/** 버튼 크기 (DESIGN.md 4장). */
enum class MapleButtonSize(val height: Dp, val radius: Dp) {
    /** 화면 하단 주 동작. 54dp, 모서리 18dp, Jua 18sp. */
    Large(MapleDimens.ButtonHeight, MapleDimens.ButtonRadius),

    /** 카드 안 보조 동작. 36dp, 모서리 12dp, Jua 14sp. */
    Small(MapleDimens.ButtonSmallHeight, MapleDimens.ButtonSmallRadius),
}

/** 버튼 색 종류 (DESIGN.md 4장, 5장 ConfirmSheet). */
enum class MapleButtonStyle {
    /** `Orange` 배경 + `OnOrange` 글자. */
    Primary,

    /** `Card` 배경 + `Edge` 테두리 + `Ink` 글자. */
    Ghost,

    /** 위험 동작. `Bad` 배경 + `OnBad` 글자. */
    Danger,
}

/**
 * 공통 버튼. 비활성은 `Track` 배경 + `Ink3` 글자.
 * [isLoading]이면 라벨 대신 로딩 표시를 보여 주고 클릭을 막는다(색은 활성 그대로).
 */
@Composable
fun MapleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: MapleButtonSize = MapleButtonSize.Large,
    style: MapleButtonStyle = MapleButtonStyle.Primary,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    val colors = MapleTheme.colors
    val shape = RoundedCornerShape(size.radius)
    val (container, content) = buttonColors(colors, style, enabled)
    val textStyle = when (size) {
        MapleButtonSize.Large -> MapleTheme.typography.button
        MapleButtonSize.Small -> MapleTheme.typography.buttonSmall
    }

    Box(
        modifier = modifier
            .height(size.height)
            .clip(shape)
            .background(container)
            .then(
                if (style == MapleButtonStyle.Ghost) Modifier.border(MapleDimens.CardBorder, colors.edge, shape)
                else Modifier,
            )
            .clickable(
                enabled = enabled && !isLoading,
                role = Role.Button,
                onClick = onClick,
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = content,
                strokeWidth = 2.dp,
                modifier = Modifier.size(if (size == MapleButtonSize.Large) 22.dp else 16.dp),
            )
        } else {
            Text(text = text, style = textStyle, color = content, maxLines = 1)
        }
    }
}

private fun buttonColors(
    colors: MapleColors,
    style: MapleButtonStyle,
    enabled: Boolean,
): Pair<Color, Color> = when {
    !enabled -> colors.track to colors.ink3
    style == MapleButtonStyle.Primary -> colors.orange to colors.onOrange
    style == MapleButtonStyle.Ghost -> colors.card to colors.ink
    else -> colors.bad to colors.onBad
}

@Composable
private fun ButtonSample(theme: MapleThemeType) {
    MapleTheme(theme = theme) {
        Column(
            modifier = Modifier
                .background(MapleTheme.colors.ground)
                .padding(MapleDimens.ScreenHorizontalPadding),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            MapleButton(text = "연결하기", onClick = {}, modifier = Modifier.fillMaxWidth())
            MapleButton(text = "연결하기", onClick = {}, modifier = Modifier.fillMaxWidth(), isLoading = true)
            MapleButton(text = "연결하기", onClick = {}, modifier = Modifier.fillMaxWidth(), enabled = false)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MapleButton(text = "계정 연결하기", onClick = {}, size = MapleButtonSize.Small, style = MapleButtonStyle.Ghost)
                MapleButton(text = "삭제", onClick = {}, size = MapleButtonSize.Small, style = MapleButtonStyle.Danger)
            }
        }
    }
}

@Preview(name = "Button · 블랙", widthDp = 360)
@Composable
private fun ButtonBlackPreview() = ButtonSample(MapleThemeType.BLACK)

@Preview(name = "Button · 베이지", widthDp = 360)
@Composable
private fun ButtonBeigePreview() = ButtonSample(MapleThemeType.BEIGE)
