package com.mosstis.mapleday.feature.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mosstis.mapleday.core.designsystem.component.MapleSplashBackground
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType
import com.mosstis.mapleday.core.designsystem.R as DesignSystemR

private const val LEAF_ANIMATION_MILLIS = 700

/**
 * S01 스플래시. 시스템 스플래시 뒤에 이어서 잎 애니메이션(0.7초)을 재생한다.
 * 기기의 애니메이션 끄기 설정(animator duration scale 0)이면 Compose 애니메이션이 즉시 끝나 정지 상태로 보인다.
 */
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    animateLeaf: Boolean = true,
) {
    val splash = MapleTheme.splash
    val leafProgress = remember { Animatable(if (animateLeaf) 0f else 1f) }

    LaunchedEffect(Unit) {
        leafProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(LEAF_ANIMATION_MILLIS, easing = FastOutSlowInEasing),
        )
    }

    MapleSplashBackground(modifier = modifier) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(DesignSystemR.drawable.ic_maple_leaf),
                    contentDescription = null,
                    tint = splash.leaf,
                    modifier = Modifier
                        .size(46.dp)
                        .graphicsLayer {
                            val p = leafProgress.value
                            alpha = p
                            scaleX = 0.6f + 0.4f * p
                            scaleY = 0.6f + 0.4f * p
                            rotationZ = -30f * (1f - p)
                        },
                )
                Text(
                    text = stringResource(R.string.splash_app_name),
                    style = MapleTheme.typography.screenTitle.copy(fontSize = 32.sp),
                    color = splash.name,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
            Text(
                text = stringResource(R.string.splash_tagline),
                style = MapleTheme.typography.cardTitle.copy(fontSize = 15.sp),
                color = splash.sub,
            )
        }
        Text(
            text = stringResource(R.string.splash_data_source),
            style = MapleTheme.typography.meta.copy(fontSize = 10.5.sp),
            color = splash.sub,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(start = 20.dp, end = 20.dp, bottom = 30.dp),
        )
    }
}

@Preview(name = "Splash · 블랙", widthDp = 360, heightDp = 720)
@Composable
private fun SplashScreenBlackPreview() {
    MapleTheme(theme = MapleThemeType.BLACK) {
        SplashScreen(animateLeaf = false)
    }
}

@Preview(name = "Splash · 베이지", widthDp = 360, heightDp = 720)
@Composable
private fun SplashScreenBeigePreview() {
    MapleTheme(theme = MapleThemeType.BEIGE) {
        SplashScreen(animateLeaf = false)
    }
}
