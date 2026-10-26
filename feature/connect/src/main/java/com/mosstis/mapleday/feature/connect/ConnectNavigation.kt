package com.mosstis.mapleday.feature.connect

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ConnectNavRoute

/** 진입 경로: S03 하단 링크, G02 미연결 안내, M04 (SPEC SC2). */
fun NavController.navigateToConnect(navOptions: NavOptions? = null) {
    navigate(route = ConnectNavRoute, navOptions = navOptions)
}

/** [onFinished]: S05에서 캐릭터 저장 완료. 다음 화면은 :app의 AppNavHost가 결정한다. */
fun NavGraphBuilder.connectScreen(
    onBack: () -> Unit,
    onFinished: () -> Unit,
) {
    composable<ConnectNavRoute> {
        ConnectRoute(onBack = onBack, onFinished = onFinished)
    }
}
