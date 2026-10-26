package com.mosstis.mapleday.core.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mosstis.mapleday.core.designsystem.theme.MapleDimens
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType
import com.mosstis.mapleday.core.ui.R

private val CheckBoxSize = 20.dp
private val CheckBoxRadius = 6.dp
private val CheckBoxBorder = 1.5.dp

/**
 * 체크박스 (DESIGN.md 5.3): 20dp, 모서리 6dp.
 * 미체크는 `BoxBg` + 1.5dp `CheckBorder`, 체크는 `Orange` 채움 + `OnOrange` 체크.
 *
 * - [onCheckedChange] == null → 고정 (`checked`에 따라 미체크 고정 / 체크 고정)
 * - [onCheckedChange] != null → 눌러서 토글
 */
@Composable
fun MapleCheckBox(
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
) {
    val colors = MapleTheme.colors
    val shape = RoundedCornerShape(CheckBoxRadius)
    val fill by animateColorAsState(if (checked) colors.orange else colors.boxBg, label = "checkFill")
    val border by animateColorAsState(if (checked) colors.orange else colors.checkBorder, label = "checkBorder")
    val tick = colors.onOrange
    val state = stringResource(
        if (checked) R.string.core_ui_checkbox_checked else R.string.core_ui_checkbox_unchecked,
    )

    val interaction = if (onCheckedChange != null) {
        Modifier.toggleable(
            value = checked,
            enabled = enabled,
            role = Role.Checkbox,
            onValueChange = onCheckedChange,
        )
    } else {
        // 고정 상태도 스크린리더에는 체크 여부를 알린다.
        Modifier.semantics { stateDescription = state }
    }

    Box(
        modifier = modifier
            .then(interaction)
            .size(CheckBoxSize)
            .clip(shape)
            .background(fill)
            .border(CheckBoxBorder, border, shape),
        contentAlignment = Alignment.Center,
    ) {
        if (checked) {
            // spec의 tick 심볼: viewBox 24 기준 M5 12.5 l4 4 L19 7, 선 3.4. 박스 대비 11/18 크기.
            Canvas(Modifier.size(CheckBoxSize * 11f / 18f)) {
                val unit = size.width / 24f
                val path = Path().apply {
                    moveTo(5f * unit, 12.5f * unit)
                    lineTo(9f * unit, 16.5f * unit)
                    lineTo(19f * unit, 7f * unit)
                }
                drawPath(
                    path = path,
                    color = tick,
                    style = Stroke(width = 3.4f * unit, cap = StrokeCap.Round, join = StrokeJoin.Round),
                )
            }
        }
    }
}

@Composable
private fun CheckBoxSample(theme: MapleThemeType) {
    MapleTheme(theme = theme) {
        val colors = MapleTheme.colors
        var toggled by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(colors.card)
                .padding(MapleDimens.CardPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            SampleRow("미체크 고정") { MapleCheckBox(checked = false) }
            SampleRow("체크 고정") { MapleCheckBox(checked = true) }
            SampleRow("토글") { MapleCheckBox(checked = toggled, onCheckedChange = { toggled = it }) }
        }
    }
}

@Composable
private fun SampleRow(label: String, box: @Composable () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(9.dp)) {
        box()
        Text(text = label, style = MapleTheme.typography.body, color = MapleTheme.colors.ink)
    }
}

@Preview(name = "CheckBox · 블랙")
@Composable
private fun CheckBoxBlackPreview() = CheckBoxSample(MapleThemeType.BLACK)

@Preview(name = "CheckBox · 베이지")
@Composable
private fun CheckBoxBeigePreview() = CheckBoxSample(MapleThemeType.BEIGE)
