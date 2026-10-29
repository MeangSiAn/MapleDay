package com.mosstis.mapleday.feature.main.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType
import com.mosstis.mapleday.core.ui.component.MapleBaseTextView
import com.mosstis.mapleday.core.ui.component.MapleProgressBar
import com.mosstis.mapleday.feature.main.R

/**
 * H01 "주간 보스" 카드: 클리어 수 / 한도 + 진행 바.
 * 한도가 0이면(아직 모름) 바는 비워 둔다.
 */
@Composable
fun WeeklyBossCard(
    clearCount: Int,
    limit: Int,
    modifier: Modifier = Modifier,
) {
    MapleBaseTextView(modifier = modifier) {
        Text(
            text = stringResource(R.string.main_weekly_boss_title),
            style = MapleTheme.typography.cardTitle.copy(fontSize = 13.5.sp),
            color = MapleTheme.colors.ink,
        )
        Spacer(Modifier.height(6.dp))
        // 큰 숫자 + 작은 "/한도" (스펙 .big + small)
        Row {
            Text(
                text = clearCount.toString(),
                style = MapleTheme.typography.bigFigure,
                color = MapleTheme.colors.ink,
                modifier = Modifier.alignByBaseline(),
            )
            Text(
                text = stringResource(R.string.main_weekly_boss_limit, limit),
                style = MapleTheme.typography.figureDenominator.copy(fontSize = 13.sp),
                color = MapleTheme.colors.ink3,
                modifier = Modifier.alignByBaseline(),
            )
        }
        Spacer(Modifier.height(6.dp))
        MapleProgressBar(progress = if (limit > 0) clearCount.toFloat() / limit else 0f)
    }
}

@Preview(name = "WeeklyBossCard · 블랙", widthDp = 180)
@Composable
private fun WeeklyBossCardBlackPreview() {
    MapleTheme(theme = MapleThemeType.BLACK) {
        WeeklyBossCard(clearCount = 9, limit = 12)
    }
}

@Preview(name = "WeeklyBossCard · 베이지", widthDp = 180)
@Composable
private fun WeeklyBossCardBeigePreview() {
    MapleTheme(theme = MapleThemeType.BEIGE) {
        WeeklyBossCard(clearCount = 9, limit = 12)
    }
}
