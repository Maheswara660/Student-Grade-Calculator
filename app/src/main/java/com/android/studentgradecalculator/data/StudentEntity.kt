package com.android.studentgradecalculator.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val rollNumber: String,
    val name: String,
    val subjectsJson: String, // Stored as JSON string
    val marksJson: String, // Stored as JSON string
    val totalMarks: Double,
    val percentage: Double,
    val gpa: Double,
    val createdAt: Long = System.currentTimeMillis()
)
