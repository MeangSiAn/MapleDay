package com.mosstis.mapleday.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mosstis.mapleday.core.ui.component.MapleBottomBar
import com.mosstis.mapleday.core.ui.component.MapleBottomBarItem

/**
 * 앱 최상단: NavHost 위에 하단 바를 겹친다.
 * 지금 화면이 [NavTab] 중 하나일 때만 바를 보인다 (Splash, Connect, 상세 화면은 숨김).
 */
@Composable
fun MapleDayApp() {
    val navController = rememberNavController()
    val currentEntry by navController.currentBackStackEntryAsState()
    val currentTab = currentEntry?.destination.toNavTab()

    Box(modifier = Modifier.fillMaxSize()) {
        AppNavHost(navController = navController)

        if (currentTab != null) {
            val tabs = NavTab.entries
            MapleBottomBar(
                items = tabs.map { MapleBottomBarItem(icon = it.icon, label = stringResource(it.labelRes)) },
                selectedIndex = tabs.indexOf(currentTab),
                onSelect = { index ->
                    val tab = tabs[index]
                    if(tab == currentTab) return@MapleBottomBar
                    navController.navigateToTab(tabs[index])
               },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}
