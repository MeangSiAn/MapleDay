package com.mosstis.mapleday.feature.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object MainNavRoute

fun NavController.navigateToMain(navOptions: NavOptions? = null) {
    navigate(route = MainNavRoute, navOptions = navOptions)
}

fun NavGraphBuilder.mainScreen() {
    composable<MainNavRoute> {
        MainRoute(

        )
    }
}
