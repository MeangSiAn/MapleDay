package com.mosstis.mapleday.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.mosstis.mapleday.core.designsystem.R

/** 제목, 큰 숫자, 배지, 캐릭터 이름, 버튼 라벨용 둥근 글씨. */
val Jua = FontFamily(Font(R.font.jua_regular))

/** 본문, 보조 텍스트용. */
val NotoSansKr = FontFamily(
    Font(R.font.noto_sans_kr_regular, FontWeight.Normal),
    Font(R.font.noto_sans_kr_medium, FontWeight.Medium),
    Font(R.font.noto_sans_kr_bold, FontWeight.Bold),
)

/**
 * 역할별 텍스트 스타일 (DESIGN.md 3장). 색은 넣지 않으며 사용처에서 [MapleColors] 토큰으로 지정한다.
 */
@Immutable
data class MapleTypography(
    val screenTitle: TextStyle,
    val onboardingTitle: TextStyle,
    val cardTitle: TextStyle,
    val characterName: TextStyle,
    val characterNameLarge: TextStyle,
    val bigFigure: TextStyle,
    val figureDenominator: TextStyle,
    val keyValue: TextStyle,
    val badge: TextStyle,
    val button: TextStyle,
    val buttonSmall: TextStyle,
    val body: TextStyle,
    val sub: TextStyle,
    val meta: TextStyle,
)

private val JuaStyle = TextStyle(fontFamily = Jua, fontWeight = FontWeight.Normal)
private val NotoStyle = TextStyle(fontFamily = NotoSansKr, fontWeight = FontWeight.Normal)

val DefaultMapleTypography = MapleTypography(
    screenTitle = JuaStyle.copy(fontSize = 26.sp, lineHeight = 1.1.em),
    onboardingTitle = JuaStyle.copy(fontSize = 30.sp, lineHeight = 1.25.em),
    cardTitle = JuaStyle.copy(fontSize = 17.sp),
    characterName = JuaStyle.copy(fontSize = 15.5.sp),
    characterNameLarge = JuaStyle.copy(fontSize = 18.sp),
    bigFigure = JuaStyle.copy(fontSize = 22.sp).numeric(),
    figureDenominator = JuaStyle.copy(fontSize = 14.sp).numeric(),
    keyValue = JuaStyle.copy(fontSize = 16.sp).numeric(),
    badge = JuaStyle.copy(fontSize = 12.sp),
    button = JuaStyle.copy(fontSize = 18.sp),
    buttonSmall = JuaStyle.copy(fontSize = 14.sp),
    body = NotoStyle.copy(fontSize = 14.sp, letterSpacing = (-0.015).em),
    sub = NotoStyle.copy(fontSize = 12.5.sp),
    meta = NotoStyle.copy(fontSize = 11.5.sp).numeric(),
)

/** 시간, EXP, 달성 수처럼 갱신되는 숫자가 흔들리지 않도록 고정폭 숫자(tnum)를 쓴다. */
fun TextStyle.numeric(): TextStyle = copy(fontFeatureSettings = "tnum")

/** Material3 컴포넌트가 기본으로 쓰는 글꼴도 같은 체계로 맞춘다. */
internal fun MapleTypography.toMaterialTypography(): Typography = Typography(
    headlineMedium = onboardingTitle,
    headlineSmall = screenTitle,
    titleLarge = JuaStyle.copy(fontSize = 22.sp),
    titleMedium = cardTitle,
    titleSmall = characterName,
    bodyLarge = NotoStyle.copy(fontSize = 16.sp),
    bodyMedium = body,
    bodySmall = sub,
    labelLarge = buttonSmall,
    labelMedium = badge,
    labelSmall = meta,
)
