package com.dak0ta.ricktionary.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dak0ta.ricktionary.core.navigation.compose.LocalNavController
import com.dak0ta.ricktionary.core.navigation.compose.LocalViewModelFactory
import com.dak0ta.ricktionary.core.navigation.directionRouteOf
import com.dak0ta.ricktionary.feature.home.domain.navigation.HomeDirection
import com.dak0ta.ricktionary.feature.home.presentation.navigation.navigationHome

@Composable
fun NavHost(
    navController: NavHostController,
    viewModelFactory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalViewModelFactory provides viewModelFactory,
        LocalNavController provides navController,
    ) {
        NavHost(
            navController = navController,
            startDestination = directionRouteOf(HomeDirection::class),
            modifier = modifier,
        ) {
            navigationHome()
        }
    }
}
