package com.mosstis.mapleday.feature.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SplashNavRoute

/**
 * 다음 화면(메인/계정 연결)은 feature 간 직접 의존을 피하기 위해 콜백으로 :app의 AppNavHost가 결정한다.
 * 저장된 내 캐릭터가 있으면 [hasCharacter], 없으면 [noCharacter].
 */
fun NavGraphBuilder.splashScreen(
    hasCharacter : () -> Unit,
    noCharacter : () -> Unit
) {
    composable<SplashNavRoute> {
        SplashRoute(
            hasCharacter = hasCharacter,
            noCharacter = noCharacter
        )
    }
}
