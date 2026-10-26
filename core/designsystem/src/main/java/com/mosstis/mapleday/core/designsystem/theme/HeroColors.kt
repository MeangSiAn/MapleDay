package com.mosstis.mapleday.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Hero 카드 그라데이션 (DESIGN.md 2.3).
 * 대표 캐릭터(H01), 경험치(G01), 강화 요약(G02)에만 쓰며 한 화면에 1~2개.
 */
@Immutable
data class HeroColors(
    val fillStart: Color,
    val fillMid: Color,
    val fillEnd: Color,
    val edgeStart: Color,
    val edgeMid: Color,
    val edgeEnd: Color,
    val midStop: Float,
)

val BlackHero = HeroColors(
    fillStart = Color(0x4DFFAB66), // FFAB66 30%
    fillMid = Color(0x00FFAB66),
    fillEnd = Color(0x336EBEAA), // 6EBEAA 20%
    edgeStart = Color(0xBFFFAB66), // 75%
    edgeMid = Color(0x0FFFFFFF), // 흰색 6%
    edgeEnd = Color(0x996EBEAA), // 60%
    midStop = 0.50f,
)

val BeigeHero = HeroColors(
    fillStart = Color(0x24F5921E), // F5921E 14%
    fillMid = Color(0x00F5921E),
    fillEnd = Color(0x1A6EBEAA), // 6EBEAA 10%
    edgeStart = Color(0x8CF5921E), // 55%
    edgeMid = Color(0xFFE5DFD2), // Edge
    edgeEnd = Color(0x736EBEAA), // 45%
    midStop = 0.55f,
)
