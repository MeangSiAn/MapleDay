package com.mosstis.mapleday.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.mosstis.mapleday.R
import com.mosstis.mapleday.core.designsystem.icon.MapleIcons
import com.mosstis.mapleday.feature.main.MainNavRoute

enum class NavTab(
    val destination : Any,
    @param:StringRes val labelRes : Int,
    val icon: ImageVector
) {
    MAIN(
        destination = MainNavRoute,
        labelRes = R.string.tab_main,
        icon = MapleIcons.Today
    )
}

fun NavDestination?.toNavTab() : NavTab? = this
    ?.hierarchy
    ?.firstNotNullOfOrNull { destination ->
        NavTab.entries.firstOrNull { tab ->
            destination.hasRoute(tab.destination::class)
        }
    }

/**
 * 첫 탭(MAIN)까지 pop하고 이동한다. 뒤로가기는 다른 탭 → 첫 탭 → 앱 종료 순서.
 * graph.findStartDestination()은 Splash인데, Splash는 진입 시 inclusive로 pop돼 백스택에 없어서
 * popUpTo 대상으로 쓰면 아무것도 pop되지 않고 saveState/restoreState도 동작하지 않는다.
 */
fun NavHostController.navigateToTab(tab : NavTab) {
    navigate(tab.destination) {
        popUpTo(NavTab.MAIN.destination) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}