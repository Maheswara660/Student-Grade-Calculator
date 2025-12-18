package com.android.studentgradecalculator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.studentgradecalculator.StudentCalculatorApp
import com.android.studentgradecalculator.data.StudentEntity
import com.google.gson.Gson
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val studentDao = (application as StudentCalculatorApp).database.studentDao()
    private val gson = Gson()

    val allStudents: StateFlow<List<StudentEntity>> = studentDao.getAllStudents()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addStudent(name: String, rollNumber: String, subjects: List<CalculatorViewModel.GradeSubject>) {
        if (name.isBlank() || rollNumber.isBlank() || subjects.isEmpty()) return

        // Calculate Totals
        var totalMarksObtained = 0.0
        var totalMaxMarks = 0.0
        var totalPoints = 0.0
        var totalWeight = 0.0

        val subjectMap = mutableMapOf<String, Double>()

        for (subject in subjects) {
            val marks = subject.marks.toDoubleOrNull() ?: 0.0
            val maxMarks = subject.totalMarks.toDoubleOrNull() ?: 100.0
            
            subjectMap[subject.name.ifBlank { "Subject" }] = marks
            
            val percentage = if (maxMarks > 0) (marks / maxMarks) * 100 else 0.0
            
            val gradePoint = if (percentage >= 90) 10.0 
                             else if (percentage >= 80) 9.0 
                             else if (percentage >= 70) 8.0 
                             else if (percentage >= 60) 7.0 
                             else if (percentage >= 50) 6.0 
                             else if (percentage >= 40) 5.0 
                             else 0.0
                             
            totalPoints += (gradePoint * maxMarks)
            totalWeight += maxMarks
            totalMarksObtained += marks
            totalMaxMarks += maxMarks
        }

        val percentage = if (totalMaxMarks > 0) (totalMarksObtained / totalMaxMarks) * 100 else 0.0
        val gpa = if (totalWeight > 0) totalPoints / totalWeight else 0.0

        val student = StudentEntity(
            name = name,
            rollNumber = rollNumber,
            subjectsJson = gson.toJson(subjects.map { it.name }), // Storing names list
            marksJson = gson.toJson(subjectMap),
            totalMarks = totalMarksObtained,
            percentage = percentage,
            gpa = gpa
        )

        viewModelScope.launch {
            studentDao.insertStudent(student)
        }
    }
    
    fun deleteStudent(student: StudentEntity) {
        viewModelScope.launch {
            studentDao.deleteStudent(student)
        }
    }
}
