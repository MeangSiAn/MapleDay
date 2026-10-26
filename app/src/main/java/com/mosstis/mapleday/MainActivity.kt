package com.mosstis.mapleday

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType
import com.mosstis.mapleday.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // TODO: 테마 설정(DataStore) 연동 전까지 블랙(기본) 고정
        val theme = MapleThemeType.BLACK
        enableEdgeToEdge(
            statusBarStyle = systemBarStyle(theme),
            navigationBarStyle = systemBarStyle(theme),
        )
        setContent {
            MapleTheme(theme = theme) {
                AppNavHost()
            }
        }
    }

    // 블랙은 밝은 아이콘, 베이지는 어두운 아이콘 (DESIGN.md 2.5)
    private fun systemBarStyle(theme: MapleThemeType): SystemBarStyle =
        if (theme.isDark) {
            SystemBarStyle.dark(Color.TRANSPARENT)
        } else {
            SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        }
}
