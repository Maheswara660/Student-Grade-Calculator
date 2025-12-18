package com.android.studentgradecalculator.ui.navigation

sealed class Screen(val route: String, val title: String) {
    object Welcome : Screen("welcome", "Welcome")
    object Home : Screen("home", "Home")
    object Calculators : Screen("calculators", "Calculators")
    object History : Screen("history", "History")
    object Settings : Screen("settings", "Settings")
    
    // Calculator Modes
    object SimpleCalculator : Screen("simple_calculator", "Basic Calculator")
    object GradeCalculator : Screen("grade_calculator", "Grade Calculator")
    object CGPACalculator : Screen("cgpa_calculator", "CGPA Calculator")
    object StudentManager : Screen("student_manager", "Multi-Student Manager")
}
