package com.dak0ta.ricktionary.core.navigation.compose

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.dak0ta.ricktionary.core.navigation.Direction
import com.dak0ta.ricktionary.core.navigation.directionRouteOf
import kotlin.reflect.KClass

fun NavController.navigateTo(
    directionClass: KClass<out Direction>,
    builder: (NavOptionsBuilder.() -> Unit)? = null,
) {
    val route = directionRouteOf(directionClass)
    if (builder == null) {
        this.navigate(route)
    } else {
        this.navigate(route, builder)
    }
}

fun NavOptionsBuilder.popUpToDirection(
    directionClass: KClass<out Direction>,
    saveState: Boolean = false,
    inclusive: Boolean = false,
) {
    popUpTo(directionRouteOf(directionClass)) {
        this.saveState = saveState
        this.inclusive = inclusive
    }
}
