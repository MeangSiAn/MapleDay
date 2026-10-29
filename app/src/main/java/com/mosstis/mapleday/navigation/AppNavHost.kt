package com.mosstis.mapleday.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.mosstis.mapleday.feature.connect.connectScreen
import com.mosstis.mapleday.feature.connect.navigateToConnect
import com.mosstis.mapleday.feature.main.mainScreen
import com.mosstis.mapleday.feature.main.navigateToMain
import com.mosstis.mapleday.feature.splash.SplashNavRoute
import com.mosstis.mapleday.feature.splash.splashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = SplashNavRoute,
        modifier = modifier,
    ) {
        splashScreen(
//            onFinished = {
//                // 뒤로가기로 스플래시에 돌아오지 않도록 백스택에서 제거
//                navController.navigateToMain(
//                    navOptions {
//                        popUpTo<SplashNavRoute> { inclusive = true }
//                    },
//                )
//            },
            hasCharacter = {
                navController.navigateToMain(
                    navOptions {
                        popUpTo<SplashNavRoute> { inclusive = true }
                    },
                )
            },
            noCharacter = {
                navController.navigateToConnect(
                    navOptions {
                        popUpTo<SplashNavRoute> { inclusive = true }
                    }
                )
            }
        )
        mainScreen()
        connectScreen(
            onBack = navController::popBackStack,
            onFinished = {
                // 백스택을 모두 비우고 Main(첫 탭)을 바닥에 둔다.
                // 탭 안에서 Connect로 들어온 경우(M04)에도 Main이 두 번 쌓이지 않게 한다 (navigateToTab의 전제)
                navController.navigateToMain(
                    navOptions {
                        popUpTo(navController.graph.id) { inclusive = true }
                    },
                )
            },
        )
    }
}
