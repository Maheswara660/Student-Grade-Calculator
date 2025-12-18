package com.android.studentgradecalculator.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.studentgradecalculator.StudentCalculatorApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPrefs = application.getSharedPreferences("settings", Context.MODE_PRIVATE)
    private val database = (application as StudentCalculatorApp).database

    private val _isDarkTheme = MutableStateFlow(sharedPrefs.getBoolean("dark_theme", false))
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _isFirstLaunch = MutableStateFlow(sharedPrefs.getBoolean("is_first_launch", true))
    val isFirstLaunch: StateFlow<Boolean> = _isFirstLaunch.asStateFlow()

    fun toggleTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        sharedPrefs.edit().putBoolean("dark_theme", isDark).apply()
    }
    
    fun completeOnboarding() {
        _isFirstLaunch.value = false
        sharedPrefs.edit().putBoolean("is_first_launch", false).apply()
    }

    fun clearAllData() {
        viewModelScope.launch {
            database.studentDao().clearAllStudents()
            database.historyDao().clearAllHistory()
        }
    }
}
