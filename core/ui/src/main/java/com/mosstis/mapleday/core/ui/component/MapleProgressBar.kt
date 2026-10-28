package com.mosstis.mapleday.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mosstis.mapleday.core.designsystem.theme.MapleDimens
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType
import com.mosstis.mapleday.core.designsystem.theme.numeric
import com.mosstis.mapleday.core.ui.R

private val BarHeight = 7.dp
private val BarBorder = 1.dp
private val BarShape = RoundedCornerShape(percent = 50)

/**
 * 가로 진행 바 (DESIGN.md 5.4 `ExpBar`의 바 부분): 높이 7dp, 트랙 `Card2` + 1dp `Edge`, 채움 `Orange`.
 * EXP, 주간 보스 n/한도 등에 쓴다.
 *
 * @param progress 0f~1f (범위를 벗어나면 잘라서 그린다)
 */
@Composable
fun MapleProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    val colors = MapleTheme.colors
    val value = progress.coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(BarHeight)
            .progressSemantics(value)
            .clip(BarShape)
            .background(colors.card2)
            .border(BarBorder, colors.edge, BarShape),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(value)
                .fillMaxHeight()
                .clip(BarShape)
                .background(colors.orange),
        )
    }
}

/**
 * 경험치 바 (DESIGN.md 5.4): 위에 "EXP" 라벨(왼쪽)과 값 "41.523%"(오른쪽, Jua 13sp `OrangeInk`), 아래에 진행 바.
 *
 * @param expRate 경험치 퍼센트 0~100 (`CharacterBasic.expRate`)
 */
@Composable
fun ExpBar(
    expRate: Double,
    modifier: Modifier = Modifier,
) {
    val colors = MapleTheme.colors
    val typography = MapleTheme.typography

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.core_ui_exp_label),
                style = typography.sub,
                color = colors.ink3,
            )
            Text(
                text = stringResource(R.string.core_ui_exp_rate, expRate),
                style = typography.badge.copy(fontSize = 13.sp).numeric(),
                color = colors.orangeInk,
            )
        }
        MapleProgressBar(progress = (expRate / 100.0).toFloat())
    }
}

@Composable
private fun ProgressBarSample(theme: MapleThemeType) {
    MapleTheme(theme = theme) {
        Column(
            modifier = Modifier
                .width(280.dp)
                .background(MapleTheme.colors.card)
                .padding(MapleDimens.CardPadding),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            MapleProgressBar(progress = 0f)
            MapleProgressBar(progress = 0.4f)
            MapleProgressBar(progress = 1f)
            ExpBar(expRate = 22.718)
        }
    }
}

@Preview(name = "ProgressBar · 블랙")
@Composable
private fun ProgressBarBlackPreview() = ProgressBarSample(MapleThemeType.BLACK)

@Preview(name = "ProgressBar · 베이지")
@Composable
private fun ProgressBarBeigePreview() = ProgressBarSample(MapleThemeType.BEIGE)
