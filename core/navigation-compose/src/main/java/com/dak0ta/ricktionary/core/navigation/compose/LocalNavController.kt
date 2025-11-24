package com.dak0ta.ricktionary.core.navigation.compose

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController: ProvidableCompositionLocal<NavHostController> = compositionLocalOf {
    error("No NavHostController provided")
}
