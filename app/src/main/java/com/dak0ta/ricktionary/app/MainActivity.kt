package com.dak0ta.ricktionary.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.dak0ta.ricktionary.app.di.appComponent
import com.dak0ta.ricktionary.app.navigation.NavHost
import com.dak0ta.ricktionary.app.ui.theme.RicktionaryTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RicktionaryTheme {
                val navController = rememberNavController()
                val viewModelFactory = appComponent.viewModelFactory()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        viewModelFactory = viewModelFactory,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
