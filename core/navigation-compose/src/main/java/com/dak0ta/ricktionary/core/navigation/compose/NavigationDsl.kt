package com.dak0ta.ricktionary.core.navigation.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dak0ta.ricktionary.core.navigation.Direction
import com.dak0ta.ricktionary.core.navigation.directionRoute
import com.dak0ta.ricktionary.core.navigation.directionRouteOf
import kotlin.reflect.KClass

inline fun <reified D : Direction> NavGraphBuilder.composableDirection(
    noinline content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(directionRoute<D>()) { backStackEntry ->
        content(backStackEntry)
    }
}

inline fun <reified G : Direction> NavGraphBuilder.navigationDirection(
    start: KClass<out Direction>,
    noinline builder: NavGraphBuilder.() -> Unit,
) {
    navigation(
        startDestination = directionRouteOf(start),
        route = directionRoute<G>(),
        builder = builder,
    )
}
