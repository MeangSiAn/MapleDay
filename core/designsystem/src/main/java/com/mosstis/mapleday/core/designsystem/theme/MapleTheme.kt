package com.mosstis.mapleday.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

/** 블랙(기본) / 베이지. 레이아웃과 컴포넌트는 같고 색 토큰만 바뀐다 (DESIGN.md 1장). */
enum class MapleThemeType {
    BLACK,
    BEIGE,
    ;

    val isDark: Boolean get() = this == BLACK
}

val LocalMapleColors = staticCompositionLocalOf { BlackColors }
val LocalHeroColors = staticCompositionLocalOf { BlackHero }
val LocalSplashColors = staticCompositionLocalOf { BlackSplash }
val LocalCharacterSlotColors = staticCompositionLocalOf { BlackCharacterSlots }
val LocalMapleTypography = staticCompositionLocalOf { DefaultMapleTypography }

@Composable
fun MapleTheme(
    theme: MapleThemeType = MapleThemeType.BLACK,
    content: @Composable () -> Unit,
) {
    val colors = when (theme) {
        MapleThemeType.BLACK -> BlackColors
        MapleThemeType.BEIGE -> BeigeColors
    }
    val hero = when (theme) {
        MapleThemeType.BLACK -> BlackHero
        MapleThemeType.BEIGE -> BeigeHero
    }
    val splash = when (theme) {
        MapleThemeType.BLACK -> BlackSplash
        MapleThemeType.BEIGE -> BeigeSplash
    }
    val slots = when (theme) {
        MapleThemeType.BLACK -> BlackCharacterSlots
        MapleThemeType.BEIGE -> BeigeCharacterSlots
    }
    val typography = DefaultMapleTypography

    CompositionLocalProvider(
        LocalMapleColors provides colors,
        LocalHeroColors provides hero,
        LocalSplashColors provides splash,
        LocalCharacterSlotColors provides slots,
        LocalMapleTypography provides typography,
    ) {
        MaterialTheme(
            colorScheme = colors.toColorScheme(isDark = theme.isDark),
            typography = typography.toMaterialTypography(),
            content = content,
        )
    }
}

/** 화면 모듈은 `MapleTheme.colors.orange`처럼 토큰만 참조한다. */
object MapleTheme {
    val colors: MapleColors
        @Composable @ReadOnlyComposable get() = LocalMapleColors.current

    val hero: HeroColors
        @Composable @ReadOnlyComposable get() = LocalHeroColors.current

    val splash: SplashColors
        @Composable @ReadOnlyComposable get() = LocalSplashColors.current

    val characterSlots: List<CharacterSlotColor>
        @Composable @ReadOnlyComposable get() = LocalCharacterSlotColors.current

    val typography: MapleTypography
        @Composable @ReadOnlyComposable get() = LocalMapleTypography.current
}

/** Material3 컴포넌트(ModalBottomSheet, TextField 등)의 기본 색도 토큰과 맞춘다. */
private fun MapleColors.toColorScheme(isDark: Boolean): ColorScheme {
    val base = if (isDark) darkColorScheme() else lightColorScheme()
    return base.copy(
        primary = orange,
        onPrimary = onOrange,
        primaryContainer = orangeSoft,
        onPrimaryContainer = orangeInk,
        secondary = teal,
        tertiary = clearInk,
        tertiaryContainer = clearBg,
        onTertiaryContainer = clearInk,
        background = ground,
        onBackground = ink,
        surface = card,
        onSurface = ink,
        surfaceVariant = card2,
        onSurfaceVariant = ink2,
        surfaceContainer = card,
        surfaceContainerHigh = raised,
        outline = edge,
        outlineVariant = edge,
        error = bad,
        onError = onBad,
        inverseSurface = toastBg,
        inverseOnSurface = toastInk,
        scrim = scrim,
    )
}
