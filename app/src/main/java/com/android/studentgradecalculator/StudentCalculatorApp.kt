package com.android.studentgradecalculator

import android.app.Application
import androidx.room.Room
import com.android.studentgradecalculator.data.AppDatabase

class StudentCalculatorApp : Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "student_calculator_db"
        ).build()
    }
}
