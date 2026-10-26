package com.mosstis.mapleday.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * 스플래시 배경·글자 색 (DESIGN.md 2.4).
 * 블랙은 단색 바탕 위에 radial 빛 2개([glowPrimary], [glowSecondary]), 베이지는 단색.
 */
@Immutable
data class SplashColors(
    val background: Color,
    val glowPrimary: Color?,
    val glowSecondary: Color?,
    val leaf: Color,
    val name: Color,
    val sub: Color,
)

val BlackSplash = SplashColors(
    background = Color(0xFF0F0F0F),
    glowPrimary = Color(0x4DFFAB66),
    glowSecondary = Color(0x336EBEAA),
    leaf = Color(0xFFFFAB66),
    name = Color(0xFFF5F5F5),
    sub = Color(0xFFA9A9A9),
)

val BeigeSplash = SplashColors(
    background = Color(0xFFF5921E),
    glowPrimary = null,
    glowSecondary = null,
    leaf = Color(0xFFFFFFFF),
    name = Color(0xFF2B1400),
    sub = Color(0xFF5A2D00),
)
