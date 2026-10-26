package com.mosstis.mapleday.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mosstis.mapleday.core.designsystem.theme.MapleDimens
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType

/**
 * 대표 캐릭터(H01), 경험치(G01), 강화 요약(G02) 전용 카드 (DESIGN.md 2.3). 한 화면에 1~2개만 쓴다.
 * ① Card 단색 → ② 135° 그라데이션 채움 → ③ 1.5dp 그라데이션 테두리. 그림자 없음.
 */
@Composable
fun HeroCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = MapleTheme.colors
    val hero = MapleTheme.hero
    val shape = RoundedCornerShape(MapleDimens.CardRadius)
    // start=Zero, end=Infinite → 왼쪽 위에서 오른쪽 아래로
    val fill = Brush.linearGradient(
        0f to hero.fillStart,
        hero.midStop to hero.fillMid,
        1f to hero.fillEnd,
        start = Offset.Zero,
        end = Offset.Infinite,
    )
    val edge = Brush.linearGradient(
        0f to hero.edgeStart,
        0.5f to hero.edgeMid,
        1f to hero.edgeEnd,
        start = Offset.Zero,
        end = Offset.Infinite,
    )

    Column(
        modifier = modifier
            .clip(shape)
            .background(colors.card)
            .background(fill)
            .border(MapleDimens.HeroBorder, edge, shape)
            .padding(MapleDimens.CardPadding),
        content = content,
    )
}

@Composable
private fun HeroCardSample(theme: MapleThemeType) {
    MapleTheme(theme = theme) {
        Column(
            modifier = Modifier
                .background(MapleTheme.colors.ground)
                .padding(MapleDimens.ScreenHorizontalPadding),
        ) {
            HeroCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "대표 캐릭터",
                    style = MapleTheme.typography.cardTitle,
                    color = MapleTheme.colors.ink,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "41.523%",
                    style = MapleTheme.typography.bigFigure,
                    color = MapleTheme.colors.orangeInk,
                )
                Text(
                    text = "21:47:12 기준",
                    style = MapleTheme.typography.meta,
                    color = MapleTheme.colors.ink3,
                )
            }
        }
    }
}

@Preview(name = "HeroCard · 블랙", widthDp = 360)
@Composable
private fun HeroCardBlackPreview() = HeroCardSample(MapleThemeType.BLACK)

@Preview(name = "HeroCard · 베이지", widthDp = 360)
@Composable
private fun HeroCardBeigePreview() = HeroCardSample(MapleThemeType.BEIGE)
