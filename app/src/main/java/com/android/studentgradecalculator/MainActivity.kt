package com.android.studentgradecalculator

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.android.studentgradecalculator.ui.navigation.AppNavigation
import com.android.studentgradecalculator.ui.navigation.Screen
import com.android.studentgradecalculator.ui.theme.StudentGradeCalculatorTheme
import com.android.studentgradecalculator.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState()
            val isFirstLaunch by settingsViewModel.isFirstLaunch.collectAsState()

            StudentGradeCalculatorTheme(darkTheme = isDarkTheme) {
                val view = LocalView.current
                if (!view.isInEditMode) {
                    SideEffect {
                        val window = (view.context as Activity).window
                        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkTheme
                    }
                }
                
                val navController = rememberNavController()
                val startDestination = if (isFirstLaunch) {
                    Screen.Welcome.route
                } else {
                    Screen.Home.route
                }
                
                AppNavigation(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }
}