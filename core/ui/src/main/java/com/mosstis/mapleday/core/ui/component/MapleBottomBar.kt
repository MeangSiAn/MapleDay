package com.mosstis.mapleday.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mosstis.mapleday.core.designsystem.icon.MapleIcons
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType

private val BarHeight = 62.dp
private val BarInset = 12.dp
private val BarInnerPadding = 5.dp
private val BarShape = RoundedCornerShape(31.dp)
private val TabShape = RoundedCornerShape(26.dp)
private val IconSize = 21.dp

/** 하단 바 탭 하나. 라우트는 모른다 (:app이 정한다). */
data class MapleBottomBarItem(
    val icon: ImageVector,
    val label: String,
)

/**
 * 떠 있는 하단 내비 (DESIGN.md 5.5, 6.2): 화면 아래·좌우 12dp 띄운 알약, 높이 62dp, 탭 균등 분할.
 * 선택 탭은 `Raised` 배경 + `Orange` 아이콘·라벨 굵게, 비선택은 `Ink3`.
 * 화면 `Box`의 아래쪽(`Alignment.BottomCenter`)에 두고, 스크롤 콘텐츠는 아래 여백을 둬서 가리지 않게 한다.
 */
@Composable
fun MapleBottomBar(
    items: List<MapleBottomBarItem>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MapleTheme.colors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(start = BarInset, end = BarInset, bottom = BarInset)
            .height(BarHeight)
            .shadow(elevation = 8.dp, shape = BarShape, clip = false)
            .clip(BarShape)
            .background(colors.navBg)
            .border(1.dp, colors.edge, BarShape)
            .padding(BarInnerPadding)
            .selectableGroup(),
    ) {
        items.forEachIndexed { index, item ->
            BottomBarTab(
                item = item,
                selected = index == selectedIndex,
                onClick = { onSelect(index) },
            )
        }
    }
}

@Composable
private fun RowScope.BottomBarTab(
    item: MapleBottomBarItem,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val colors = MapleTheme.colors
    val contentColor = if (selected) colors.orange else colors.ink3

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clip(TabShape)
            .background(if (selected) colors.raised else Color.Transparent)
            .selectable(selected = selected, onClick = onClick, role = Role.Tab),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = item.icon,
                contentDescription = null, // 라벨이 같이 읽힌다
                tint = contentColor,
                modifier = Modifier.size(IconSize),
            )
            Text(
                text = item.label,
                style = MapleTheme.typography.sub.copy(
                    fontSize = 10.5.sp,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                ),
                color = contentColor,
            )
        }
    }
}

@Composable
private fun BottomBarSample(theme: MapleThemeType, items: List<MapleBottomBarItem>) {
    MapleTheme(theme = theme) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MapleTheme.colors.ground),
            contentAlignment = Alignment.BottomCenter,
        ) {
            MapleBottomBar(items = items, selectedIndex = 0, onSelect = {})
        }
    }
}

private val PreviewOneTab = listOf(MapleBottomBarItem(MapleIcons.Today, "메인"))

private val PreviewFiveTabs = listOf(
    MapleBottomBarItem(MapleIcons.Today, "today"),
    MapleBottomBarItem(MapleIcons.Calendar, "일정"),
    MapleBottomBarItem(MapleIcons.Coordi, "코디"),
    MapleBottomBarItem(MapleIcons.Growth, "성장"),
    MapleBottomBarItem(MapleIcons.More, "더보기"),
)

@Preview(name = "BottomBar · 메인 1개 · 블랙", widthDp = 360, heightDp = 160)
@Composable
private fun BottomBarOneBlackPreview() = BottomBarSample(MapleThemeType.BLACK, PreviewOneTab)

@Preview(name = "BottomBar · 5탭 · 블랙", widthDp = 360, heightDp = 160)
@Composable
private fun BottomBarFiveBlackPreview() = BottomBarSample(MapleThemeType.BLACK, PreviewFiveTabs)

@Preview(name = "BottomBar · 5탭 · 베이지", widthDp = 360, heightDp = 160)
@Composable
private fun BottomBarFiveBeigePreview() = BottomBarSample(MapleThemeType.BEIGE, PreviewFiveTabs)
