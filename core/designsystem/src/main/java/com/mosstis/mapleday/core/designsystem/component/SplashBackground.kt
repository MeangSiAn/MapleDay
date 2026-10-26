package com.mosstis.mapleday.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mosstis.mapleday.core.designsystem.R
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType
import com.mosstis.mapleday.core.designsystem.theme.SplashColors

/**
 * 스플래시 배경 (DESIGN.md 2.4).
 * 블랙: #0F0F0F 위에 오렌지 빛(중심 50%·42%, 반지름 높이 36%)과 청록 빛(중심 80%·100%, 반지름 높이 25%).
 * 베이지: 오렌지 단색.
 */
fun Modifier.mapleSplashBackground(colors: SplashColors): Modifier = this
    .background(colors.background)
    .drawBehind {
        colors.glowPrimary?.let { glow ->
            drawRect(
                Brush.radialGradient(
                    colors = listOf(glow, Color.Transparent),
                    center = Offset(size.width * 0.5f, size.height * 0.42f),
                    radius = size.height * 0.36f,
                ),
            )
        }
        colors.glowSecondary?.let { glow ->
            drawRect(
                Brush.radialGradient(
                    colors = listOf(glow, Color.Transparent),
                    center = Offset(size.width * 0.8f, size.height),
                    radius = size.height * 0.25f,
                ),
            )
        }
    }

/** 현재 [MapleTheme]의 스플래시 색으로 전체 화면 배경을 그린다. */
@Composable
fun MapleSplashBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .mapleSplashBackground(MapleTheme.splash),
        content = content,
    )
}

@Composable
private fun SplashSample(theme: MapleThemeType) {
    MapleTheme(theme = theme) {
        val splash = MapleTheme.splash
        MapleSplashBackground {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_maple_leaf),
                        contentDescription = null,
                        tint = splash.leaf,
                        modifier = Modifier.size(46.dp),
                    )
                    Text(
                        text = "Maple Days",
                        style = MapleTheme.typography.screenTitle.copy(fontSize = 32.sp),
                        color = splash.name,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
                Text(
                    text = "오늘의 모험을 한 권에",
                    style = MapleTheme.typography.cardTitle.copy(fontSize = 15.sp),
                    color = splash.sub,
                )
            }
            Text(
                text = "Data based on NEXON Open API",
                style = MapleTheme.typography.meta.copy(fontSize = 10.5.sp),
                color = splash.sub,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 30.dp),
            )
        }
    }
}

@Preview(name = "Splash · 블랙", widthDp = 360, heightDp = 720)
@Composable
private fun SplashBlackPreview() = SplashSample(MapleThemeType.BLACK)

@Preview(name = "Splash · 베이지", widthDp = 360, heightDp = 720)
@Composable
private fun SplashBeigePreview() = SplashSample(MapleThemeType.BEIGE)
