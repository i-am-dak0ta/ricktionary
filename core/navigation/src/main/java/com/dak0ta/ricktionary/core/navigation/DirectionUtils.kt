package com.dak0ta.ricktionary.core.navigation

import kotlin.reflect.KClass

inline fun <reified T : Direction> directionRoute(): String = directionRouteOf(T::class)

fun <T : Direction> directionRouteOf(directionClass: KClass<T>): String =
    directionClass.qualifiedName ?: directionClass.simpleName ?: "unknown_route"
