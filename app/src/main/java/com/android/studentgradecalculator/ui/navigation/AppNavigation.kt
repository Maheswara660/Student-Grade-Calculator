package com.android.studentgradecalculator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.studentgradecalculator.ui.calculator.CalculatorsScreen
import com.android.studentgradecalculator.ui.calculator.SimpleCalculatorScreen
import com.android.studentgradecalculator.ui.cgpa.CGPACalculatorScreen
import com.android.studentgradecalculator.ui.grade.GradeCalculatorScreen
import com.android.studentgradecalculator.ui.history.HistoryScreen
import com.android.studentgradecalculator.ui.home.HomeScreen
import com.android.studentgradecalculator.ui.manager.StudentManagerScreen
import com.android.studentgradecalculator.ui.settings.SettingsScreen
import com.android.studentgradecalculator.ui.welcome.WelcomeScreen
import com.android.studentgradecalculator.viewmodel.SettingsViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Welcome.route,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onGetStarted = {
                    settingsViewModel.completeOnboarding()
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Calculators.route) {
            CalculatorsScreen(navController = navController)
        }
        composable(Screen.History.route) {
            HistoryScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        
        // Detailed screens
        composable(Screen.SimpleCalculator.route) {
            SimpleCalculatorScreen(navController = navController)
        }
        composable(Screen.GradeCalculator.route) {
            GradeCalculatorScreen(navController = navController)
        }
        composable(Screen.CGPACalculator.route) {
            CGPACalculatorScreen(navController = navController)
        }
        composable(Screen.StudentManager.route) {
            StudentManagerScreen(navController = navController)
        }
    }
}
