package com.mosstis.mapleday.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * 컬러 토큰 (DESIGN.md 2장). 컴포넌트는 토큰 이름만 쓰고 hex를 직접 쓰지 않는다.
 * 반투명 색은 ARGB(0xAARRGGBB).
 */
@Immutable
data class MapleColors(
    val ground: Color,
    val card: Color,
    val card2: Color,
    val edge: Color,
    val ink: Color,
    val ink2: Color,
    val ink3: Color,
    val orange: Color,
    val onOrange: Color,
    val orangeInk: Color,
    val orangeSoft: Color,
    val clearBg: Color,
    val clearInk: Color,
    val bad: Color,
    val onBad: Color,
    val teal: Color,
    val raised: Color,
    val navBg: Color,
    val boxBg: Color,
    val pickBg: Color,
    val track: Color,
    val checkBorder: Color,
    val ringTrack: Color,
    val ruled: Color,
    val toastBg: Color,
    val toastInk: Color,
    val scrim: Color,
)

val BlackColors = MapleColors(
    ground = Color(0xFF141414),
    card = Color(0xFF222222),
    card2 = Color(0xFF2C2C2C),
    edge = Color(0xFF333333),
    ink = Color(0xFFF5F5F5),
    ink2 = Color(0xFFA9A9A9),
    ink3 = Color(0xFF8C8C8C),
    orange = Color(0xFFFFAB66),
    onOrange = Color(0xFF2B1400),
    orangeInk = Color(0xFFFFBF8A),
    orangeSoft = Color(0x29FFAB66), // 카드 위 합성색 #45382D
    clearBg = Color(0x29FFD36B), // 카드 위 합성색 #453E2E
    clearInk = Color(0xFFFFD36B),
    bad = Color(0xFFFF6F62),
    onBad = Color(0xFF2A0805),
    teal = Color(0xFF6EBEAA),
    raised = Color(0xFF3A3A3A),
    navBg = Color(0xF5242424),
    boxBg = Color(0xFF1B1B1B),
    pickBg = Color(0x14FFAB66), // 카드 위 합성색 #342D27
    track = Color(0xFF444444),
    checkBorder = Color(0xFF555555),
    ringTrack = Color(0xFF333333),
    ruled = Color(0xFF333333),
    toastBg = Color(0xF2F5F5F5),
    toastInk = Color(0xFF161616),
    scrim = Color(0x99000000),
)

val BeigeColors = MapleColors(
    ground = Color(0xFFF0ECE3),
    card = Color(0xFFFBF9F4),
    card2 = Color(0xFFF5F1E8),
    edge = Color(0xFFE5DFD2),
    ink = Color(0xFF2C2620),
    ink2 = Color(0xFF6F675E),
    ink3 = Color(0xFF7A7166),
    orange = Color(0xFFF5921E),
    onOrange = Color(0xFF2B1400),
    orangeInk = Color(0xFFA65404),
    orangeSoft = Color(0xFFFDE8CF),
    clearBg = Color(0xFFFFF0BF),
    clearInk = Color(0xFF8F6400),
    bad = Color(0xFFCF3F32),
    onBad = Color(0xFFFFFFFF),
    teal = Color(0xFF2F8F7A),
    raised = Color(0xFFFFFFFF),
    navBg = Color(0xF5FBF9F4),
    boxBg = Color(0xFFFFFFFF),
    pickBg = Color(0xFFFFFAF3),
    track = Color(0xFFDDD5C8),
    checkBorder = Color(0xFFCFC6B8),
    ringTrack = Color(0xFFEFE8DA),
    ruled = Color(0xFFECE5D6),
    toastBg = Color(0xEB2C2620),
    toastInk = Color(0xFFFFFFFF),
    scrim = Color(0x66281E14),
)

/**
 * 캐릭터 식별 색 (DESIGN.md 2.2). 캐릭터 추가 순서대로 슬롯 0~3을 순환 배정한다.
 * 초상 배경은 [center] → [outer] radial gradient(중심 가로 50%·세로 90%, 외곽 stop은 [outerStop]).
 */
@Immutable
data class CharacterSlotColor(
    val figure: Color,
    val center: Color,
    val outer: Color,
    val outerStop: Float,
)

private val BlackSlotOuter = Color(0xFF262626)

val BlackCharacterSlots = listOf(
    CharacterSlotColor(Color(0xFFF5921E), Color(0xFF78593E), BlackSlotOuter, outerStop = 0.72f),
    CharacterSlotColor(Color(0xFF8B7FE0), Color(0xFF4C486D), BlackSlotOuter, outerStop = 0.72f),
    CharacterSlotColor(Color(0xFF4FB27E), Color(0xFF365B47), BlackSlotOuter, outerStop = 0.72f),
    CharacterSlotColor(Color(0xFFC69563), Color(0xFF63503D), BlackSlotOuter, outerStop = 0.72f),
)

val BeigeCharacterSlots = listOf(
    CharacterSlotColor(Color(0xFFF5921E), Color(0xFFFDE3C4), Color(0xFFFFF6EA), outerStop = 0.70f),
    CharacterSlotColor(Color(0xFF8B7FE0), Color(0xFFE3DFFB), Color(0xFFF7F5FF), outerStop = 0.70f),
    CharacterSlotColor(Color(0xFF4FB27E), Color(0xFFD6F1E2), Color(0xFFF3FBF6), outerStop = 0.70f),
    CharacterSlotColor(Color(0xFFC69563), Color(0xFFF1E2D1), Color(0xFFFBF6F0), outerStop = 0.70f),
)

/** 슬롯 인덱스(캐릭터 추가 순서)를 0~3으로 순환시켜 색을 고른다. */
fun List<CharacterSlotColor>.forSlot(index: Int): CharacterSlotColor = this[Math.floorMod(index, size)]
