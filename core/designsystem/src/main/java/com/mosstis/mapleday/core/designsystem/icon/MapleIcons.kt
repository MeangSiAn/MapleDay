package com.mosstis.mapleday.core.designsystem.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType

/**
 * 하단 내비 탭 아이콘 (maple-days-spec.html `#i-today` 등 SVG 그대로, viewport 24).
 * 색은 `Icon(tint = …)`로 정한다 (SVG의 currentColor).
 */
object MapleIcons {

    /** today: 2×2 둥근 사각, 대각선 두 칸 반투명 */
    val Today: ImageVector by lazy {
        icon("Today") {
            fillPath(roundRect(3f, 3f, 8f, 8f, 2.5f))
            fillPath(roundRect(13f, 3f, 8f, 8f, 2.5f), alpha = 0.5f)
            fillPath(roundRect(3f, 13f, 8f, 8f, 2.5f), alpha = 0.5f)
            fillPath(roundRect(13f, 13f, 8f, 8f, 2.5f))
        }
    }

    /** 일정: 달력 */
    val Calendar: ImageVector by lazy {
        icon("Calendar") {
            strokePath(roundRect(3.5f, 5f, 17f, 15.5f, 4f), width = 1.8f)
            strokePath("M3.5 10h17M8.5 3v4M15.5 3v4", width = 1.8f, cap = StrokeCap.Round)
        }
    }

    /** 코디: 티셔츠 실루엣 */
    val Coordi: ImageVector by lazy {
        icon("Coordi") {
            strokePath("M8 3l4 2 4-2 5 4-3 3-2-1v12H8V9L6 10 3 7z", width = 1.8f, join = StrokeJoin.Round)
        }
    }

    /** 성장: 오르는 꺾은선 + 화살표 */
    val Growth: ImageVector by lazy {
        icon("Growth") {
            strokePath("M4 19l5-6 4 3 7-9", width = 1.9f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            strokePath("M15 7h5v5", width = 1.9f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        }
    }

    /** 더보기: 가로줄 3개 */
    val More: ImageVector by lazy {
        icon("More") {
            strokePath("M4 7h16M4 12h16M4 17h16", width = 1.9f, cap = StrokeCap.Round)
        }
    }
}

private val IconColor = SolidColor(Color.Black)

private inline fun icon(name: String, block: ImageVector.Builder.() -> Unit): ImageVector =
    ImageVector.Builder(
        name = name,
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply(block).build()

private fun ImageVector.Builder.fillPath(d: String, alpha: Float = 1f) {
    addPath(pathData = addPathNodes(d), fill = IconColor, fillAlpha = alpha)
}

private fun ImageVector.Builder.strokePath(
    d: String,
    width: Float,
    cap: StrokeCap = StrokeCap.Butt,
    join: StrokeJoin = StrokeJoin.Miter,
) {
    addPath(
        pathData = addPathNodes(d),
        stroke = IconColor,
        strokeLineWidth = width,
        strokeLineCap = cap,
        strokeLineJoin = join,
    )
}

/** SVG `<rect x y width height rx>`와 같은 모양의 path 문자열 */
private fun roundRect(x: Float, y: Float, w: Float, h: Float, r: Float): String {
    val right = x + w
    val bottom = y + h
    return "M${x + r} $y H${right - r} A$r $r 0 0 1 $right ${y + r} " +
        "V${bottom - r} A$r $r 0 0 1 ${right - r} $bottom " +
        "H${x + r} A$r $r 0 0 1 $x ${bottom - r} " +
        "V${y + r} A$r $r 0 0 1 ${x + r} $y Z"
}

@Composable
private fun IconsSample(theme: MapleThemeType) {
    MapleTheme(theme = theme) {
        val colors = MapleTheme.colors
        val icons = listOf(MapleIcons.Today, MapleIcons.Calendar, MapleIcons.Coordi, MapleIcons.Growth, MapleIcons.More)
        Column(
            modifier = Modifier
                .background(colors.card)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                icons.forEach { Icon(it, contentDescription = null, tint = colors.ink3, modifier = Modifier.size(21.dp)) }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                icons.forEach { Icon(it, contentDescription = null, tint = colors.orange, modifier = Modifier.size(21.dp)) }
            }
        }
    }
}

@Preview(name = "MapleIcons · 블랙")
@Composable
private fun MapleIconsBlackPreview() = IconsSample(MapleThemeType.BLACK)

@Preview(name = "MapleIcons · 베이지")
@Composable
private fun MapleIconsBeigePreview() = IconsSample(MapleThemeType.BEIGE)
