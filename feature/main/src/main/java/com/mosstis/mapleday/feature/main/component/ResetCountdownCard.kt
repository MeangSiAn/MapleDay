package com.mosstis.mapleday.feature.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import com.mosstis.mapleday.feature.main.R
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

/**
 * H01 "초기화까지" 카드: 일일(00:00 KST)·주간(목 00:00 KST)까지 남은 시간.
 * 남은 시간 계산은 바깥에서 하고, 이 카드는 받은 값을 그리기만 한다. null이면 "-".
 */
@Composable
fun ResetCountdownCard(
    daily: Duration?,
    weekly: Duration?,
    modifier: Modifier = Modifier,
) {
    MapleBaseTextView(modifier = modifier) {
        Text(
            text = stringResource(R.string.main_reset_title),
            style = MapleTheme.typography.cardTitle.copy(fontSize = 13.5.sp),
            color = MapleTheme.colors.ink,
        )
        Spacer(Modifier.height(6.dp))
        RemainingRow(
            label = stringResource(R.string.main_reset_daily),
            value = daily?.let { stringResource(R.string.main_remaining_hours_minutes, it.inWholeHours, it.inWholeMinutes % 60) },
        )
        Spacer(Modifier.height(6.dp))
        RemainingRow(
            label = stringResource(R.string.main_reset_weekly),
            value = weekly?.let { stringResource(R.string.main_remaining_days_hours, it.inWholeDays, it.inWholeHours % 24) },
        )
    }
}

// 왼쪽 라벨, 오른쪽 값 (스펙 .kv)
@Composable
private fun RemainingRow(label: String, value: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MapleTheme.typography.sub.copy(fontSize = 11.5.sp),
            color = MapleTheme.colors.ink2,
            modifier = Modifier.alignByBaseline(),
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = value ?: stringResource(R.string.main_remaining_unknown),
            style = MapleTheme.typography.keyValue.copy(fontSize = 14.5.sp),
            color = MapleTheme.colors.ink,
            modifier = Modifier.alignByBaseline(),
        )
    }
}

@Preview(name = "ResetCountdownCard · 블랙", widthDp = 180)
@Composable
private fun ResetCountdownCardBlackPreview() {
    MapleTheme(theme = MapleThemeType.BLACK) {
        ResetCountdownCard(daily = 2.hours + 12.minutes, weekly = 2.days + 2.hours)
    }
}

@Preview(name = "ResetCountdownCard · 베이지", widthDp = 180)
@Composable
private fun ResetCountdownCardBeigePreview() {
    MapleTheme(theme = MapleThemeType.BEIGE) {
        ResetCountdownCard(daily = 2.hours + 12.minutes, weekly = 2.days + 2.hours)
    }
}
