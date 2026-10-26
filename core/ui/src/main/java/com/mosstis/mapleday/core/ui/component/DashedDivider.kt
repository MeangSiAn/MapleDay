package com.mosstis.mapleday.core.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mosstis.mapleday.core.designsystem.theme.MapleDimens
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType

/**
 * 가로 점선 구분선 (DESIGN.md 4장): 1dp `Edge`, dash 4dp gap 3dp.
 * 체크리스트·리스트 행 사이에 쓴다. 마지막 행 아래에는 두지 않는다 — 호출하는 쪽에서 처리.
 */
@Composable
fun MapleDashedDivider(
    modifier: Modifier = Modifier,
    color: Color = MapleTheme.colors.edge,
    thickness: Dp = MapleDimens.CardBorder,
    dash: Dp = MapleDimens.DashedDividerDash,
    gap: Dp = MapleDimens.DashedDividerGap,
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness),
    ) {
        val y = size.height / 2
        drawLine(
            color = color,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = thickness.toPx(),
            cap = StrokeCap.Butt,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dash.toPx(), gap.toPx())),
        )
    }
}

@Composable
private fun DashedDividerSample(theme: MapleThemeType) {
    MapleTheme(theme = theme) {
        val colors = MapleTheme.colors
        val rows = listOf("계정 캐릭터 한 번에 불러오기", "스타포스·큐브 강화 일지", "업적 모아 보기")
        Column(
            modifier = Modifier
                .background(colors.ground)
                .padding(MapleDimens.ScreenHorizontalPadding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(MapleDimens.CardRadius))
                    .background(colors.card)
                    .border(MapleDimens.CardBorder, colors.edge, RoundedCornerShape(MapleDimens.CardRadius))
                    .padding(MapleDimens.CardPadding),
            ) {
                rows.forEachIndexed { index, text ->
                    Text(
                        text = text,
                        style = MapleTheme.typography.body,
                        color = colors.ink,
                        modifier = Modifier
                            .heightIn(min = 34.dp)
                            .padding(vertical = 8.dp),
                    )
                    if (index != rows.lastIndex) MapleDashedDivider()
                }
            }
        }
    }
}

@Preview(name = "DashedDivider · 블랙", widthDp = 360)
@Composable
private fun DashedDividerBlackPreview() = DashedDividerSample(MapleThemeType.BLACK)

@Preview(name = "DashedDivider · 베이지", widthDp = 360)
@Composable
private fun DashedDividerBeigePreview() = DashedDividerSample(MapleThemeType.BEIGE)
