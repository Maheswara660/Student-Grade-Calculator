package com.android.studentgradecalculator.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.studentgradecalculator.StudentCalculatorApp
import com.android.studentgradecalculator.data.HistoryEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {

    private val historyDao = (application as StudentCalculatorApp).database.historyDao()
    private val sharedPrefs = application.getSharedPreferences("calculator_prefs", Context.MODE_PRIVATE)

    // --- Simple Calculator State ---
    private val _calculatorDisplay = MutableStateFlow("0")
    val calculatorDisplay: StateFlow<String> = _calculatorDisplay.asStateFlow()
    
    private val _calculationHistory = MutableStateFlow<List<String>>(emptyList())
    val calculationHistory: StateFlow<List<String>> = _calculationHistory.asStateFlow()
    
    private val _previousCalculation = MutableStateFlow("")
    val previousCalculation: StateFlow<String> = _previousCalculation.asStateFlow()
    
    private var memoryValue = 0.0

    // --- Grade Calculator State ---
    private val _gradeSubjects = MutableStateFlow<List<GradeSubject>>(emptyList())
    val gradeSubjects: StateFlow<List<GradeSubject>> = _gradeSubjects.asStateFlow()

    private val _gradeResult = MutableStateFlow("")
    val gradeResult: StateFlow<String> = _gradeResult.asStateFlow()
    
    // Calculation Mode (GPA or Percentage)
    enum class CalculationMode {
        GPA, PERCENTAGE, BOTH
    }
    
    private val _calculationMode = MutableStateFlow(
        // Load saved mode from SharedPreferences
        when (application.getSharedPreferences("calculator_prefs", Context.MODE_PRIVATE)
            .getString("calculation_mode", "GPA")) {
            "PERCENTAGE" -> CalculationMode.PERCENTAGE
            else -> CalculationMode.GPA
        }
    )
    val calculationMode: StateFlow<CalculationMode> = _calculationMode.asStateFlow()
    
    fun setCalculationMode(mode: CalculationMode) {
        _calculationMode.value = mode
        // Save mode to SharedPreferences
        sharedPrefs.edit().putString("calculation_mode", mode.name).apply()
        // Recalculate with new mode if there's existing data
        if (_gradeSubjects.value.isNotEmpty()) {
            calculateGPA()
        }
    }

    fun addSubject() {
        _gradeSubjects.value = _gradeSubjects.value + GradeSubject()
    }

    fun removeSubject(index: Int) {
        val currentList = _gradeSubjects.value.toMutableList()
        if (index in currentList.indices) {
            currentList.removeAt(index)
            _gradeSubjects.value = currentList
        }
    }

    fun updateSubjectName(index: Int, name: String) {
        val currentList = _gradeSubjects.value.toMutableList()
        if (index in currentList.indices) {
            currentList[index] = currentList[index].copy(name = name)
            _gradeSubjects.value = currentList
        }
    }

    fun updateSubjectMarks(index: Int, marks: String) {
        val currentList = _gradeSubjects.value.toMutableList()
        if (index in currentList.indices) {
            currentList[index] = currentList[index].copy(marks = marks)
            _gradeSubjects.value = currentList
        }
    }
    
    fun updateSubjectTotalMarks(index: Int, totalMarks: String) {
        val currentList = _gradeSubjects.value.toMutableList()
        if (index in currentList.indices) {
            currentList[index] = currentList[index].copy(totalMarks = totalMarks)
            _gradeSubjects.value = currentList
        }
    }

    fun calculateGPA() {
        val subjects = _gradeSubjects.value
        var totalPoints = 0.0
        var totalWeight = 0.0
        var totalMarksObtained = 0.0
        var totalMaxMarks = 0.0
        
        val sb = StringBuilder()
        
        try {
            for (subject in subjects) {
                // If total marks is empty or 0, we can't calculate properly for this subject.
                // We should probably skip it or assume it's incomplete.
                val maxMarks = subject.totalMarks.toDoubleOrNull() ?: 0.0
                if (maxMarks <= 0.0) continue // Skip invalid subjects

                val marks = subject.marks.toDoubleOrNull() ?: 0.0
                
                // Calculate percentage for this subject to determine Grade Point
                val percentage = (marks / maxMarks) * 100
                
                // Standard 10-point scale approximation based on percentage
                val gradePoint = if (percentage >= 90) 10.0 
                                 else if (percentage >= 80) 9.0 
                                 else if (percentage >= 70) 8.0 
                                 else if (percentage >= 60) 7.0 
                                 else if (percentage >= 50) 6.0 
                                 else if (percentage >= 40) 5.0 
                                 else 0.0
                                 
                // Weighted GPA
                totalPoints += (gradePoint * maxMarks)
                totalWeight += maxMarks
                
                totalMarksObtained += marks
                totalMaxMarks += maxMarks
            }

            if (totalWeight > 0) {
                val gpa = totalPoints / totalWeight
                val overallPercentage = (totalMarksObtained / totalMaxMarks) * 100
                
                val df = DecimalFormat("#.##")
                
                // Format result based on selected mode
                when (_calculationMode.value) {
                    CalculationMode.GPA -> {
                        sb.append("GPA: ${df.format(gpa)}\n")
                        sb.append("Total Marks: ${totalMarksObtained.toInt()} / ${totalMaxMarks.toInt()}")
                        saveHistory("GRADE", "Subjects: ${subjects.size}", "GPA: ${df.format(gpa)}")
                    }
                    CalculationMode.PERCENTAGE -> {
                        sb.append("Percentage: ${df.format(overallPercentage)}%\n")
                        sb.append("Total Marks: ${totalMarksObtained.toInt()} / ${totalMaxMarks.toInt()}")
                        saveHistory("GRADE", "Subjects: ${subjects.size}", "Percentage: ${df.format(overallPercentage)}%")
                    }
                    CalculationMode.BOTH -> {
                        sb.append("GPA: ${df.format(gpa)}\n")
                        sb.append("Percentage: ${df.format(overallPercentage)}%\n")
                        sb.append("Total Marks: ${totalMarksObtained.toInt()} / ${totalMaxMarks.toInt()}")
                        saveHistory("GRADE", "Subjects: ${subjects.size}", "GPA: ${df.format(gpa)} | ${df.format(overallPercentage)}%")
                    }
                }
                
                _gradeResult.value = sb.toString()
            } else {
                 _gradeResult.value = "Enter valid marks and total marks"
            }
        } catch (e: Exception) {
            _gradeResult.value = "Error in calculation"
        }
    }

    data class GradeSubject(
        val name: String = "",
        val marks: String = "",
        val totalMarks: String = "" // Default empty to force manual entry
    )

    // --- CGPA Calculator State ---
    private val _selectedSemesterCount = MutableStateFlow(4) // Default to 4 semesters
    val selectedSemesterCount: StateFlow<Int> = _selectedSemesterCount.asStateFlow()
    
    private val _cgpaSubjects = MutableStateFlow<List<CGPASemester>>(
        List(4) { CGPASemester(semesterNumber = it + 1) }
    )
    val cgpaSubjects: StateFlow<List<CGPASemester>> = _cgpaSubjects.asStateFlow()
    
    private val _cgpaResult = MutableStateFlow("")
    val cgpaResult: StateFlow<String> = _cgpaResult.asStateFlow()
    
    data class CGPASemester(
        val semesterNumber: Int,
        val sgpa: String = ""
    )
    
    fun setSemesterCount(count: Int) {
        _selectedSemesterCount.value = count
        _cgpaSubjects.value = List(count) { index ->
            // Preserve existing SGPA values if available
            _cgpaSubjects.value.getOrNull(index) ?: CGPASemester(semesterNumber = index + 1)
        }
        _cgpaResult.value = "" // Clear result when changing semester count
    }
    
    fun updateSemesterSGPA(index: Int, sgpa: String) {
        val currentList = _cgpaSubjects.value.toMutableList()
        if (index in currentList.indices) {
            currentList[index] = currentList[index].copy(sgpa = sgpa)
            _cgpaSubjects.value = currentList
        }
    }
    
    fun calculateCGPA() {
        val semesters = _cgpaSubjects.value
        var totalSGPA = 0.0
        var validCount = 0
        
        try {
            for (semester in semesters) {
                val sgpa = semester.sgpa.toDoubleOrNull()
                if (sgpa != null && sgpa > 0.0) {
                    totalSGPA += sgpa
                    validCount++
                }
            }
            
            if (validCount > 0) {
                val cgpa = totalSGPA / validCount
                val df = DecimalFormat("#.##")
                _cgpaResult.value = "CGPA: ${df.format(cgpa)}\nSemesters: $validCount"
                saveHistory("CGPA", "Semesters: $validCount", "CGPA: ${df.format(cgpa)}")
            } else {
                _cgpaResult.value = "Enter valid SGPA values"
            }
        } catch (e: Exception) {
            _cgpaResult.value = "Error in calculation"
        }
    }

    fun onCalculatorInput(input: String) {
        val current = _calculatorDisplay.value
        when (input) {
            "C" -> {
                _calculatorDisplay.value = "0"
                _previousCalculation.value = ""
                _calculationHistory.value = emptyList()
            }
            "MC" -> memoryValue = 0.0
            "MR" -> {
                val value = formatResult(memoryValue)
                if (_calculatorDisplay.value == "0") _calculatorDisplay.value = value
                else _calculatorDisplay.value += value
            }
            "M+" -> {
                val currentVal = try {
                    ExpressionBuilder(_calculatorDisplay.value).build().evaluate()
                } catch (e: Exception) { 0.0 }
                memoryValue += currentVal
                _calculatorDisplay.value = "0" // Standard calculator behavior often clears or keeps. Resetting for next input.
            }
            "M-" -> {
                 val currentVal = try {
                    ExpressionBuilder(_calculatorDisplay.value).build().evaluate()
                } catch (e: Exception) { 0.0 }
                memoryValue -= currentVal
                 _calculatorDisplay.value = "0"
            }
            "DEL" -> {
                 if (current.length > 1) {
                     _calculatorDisplay.value = current.dropLast(1)
                 } else {
                     _calculatorDisplay.value = "0"
                 }
            }
            "%" -> {
                // Simple percentage: divide current number by 100
                // For complex usage (100 + 10%) need more parsing, keeping it simple for now as 10% -> 0.1
                 try {
                     // If it's a standalone number, divide by 100
                     // If it's part of expression, append "/100"
                     if (current.toDoubleOrNull() != null) {
                         val valNum = current.toDouble() / 100
                         _calculatorDisplay.value = formatResult(valNum)
                     } else {
                         _calculatorDisplay.value = current + "/100"
                     }
                 } catch (e: Exception) {
                      _calculatorDisplay.value = current + "/100"
                 }
            }
            "=" -> calculateResult()
            else -> {
                if (current == "0" && !isOperator(input)) {
                    _calculatorDisplay.value = input
                } else {
                    _calculatorDisplay.value = current + input
                }
            }
        }
    }

    private fun isOperator(input: String): Boolean {
        return input in listOf("+", "-", "*", "/", "(", ")", ".")
    }

    private fun calculateResult() {
        val expressionString = _calculatorDisplay.value
        try {
            val expression = ExpressionBuilder(expressionString).build()
            val result = expression.evaluate()
            
            val formattedResult = formatResult(result)
            
            // Store previous calculation
            _previousCalculation.value = "$expressionString = $formattedResult"
            
            // Add to calculation history (keep last 5)
            val newHistory = (_calculationHistory.value + "$expressionString = $formattedResult").takeLast(5)
            _calculationHistory.value = newHistory
            
            _calculatorDisplay.value = formattedResult
            
            // Save to History
            saveHistory("SIMPLE", expressionString, formattedResult)
            
        } catch (e: Exception) {
            _calculatorDisplay.value = "Error"
        }
    }

    private fun saveHistory(type: String, input: String, result: String) {
        viewModelScope.launch {
            val entity = HistoryEntity(
                type = type,
                inputData = input,
                result = result,
                timestamp = System.currentTimeMillis()
            )
            historyDao.insertHistory(entity)
        }
    }
    
    private fun formatResult(value: Double): String {
        val df = DecimalFormat("#.##########")
        return df.format(value)
    }
    
    fun restoreHistoryItem(item: HistoryEntity) {
        if (item.type == "SIMPLE") {
            // Restore input
            _calculatorDisplay.value = item.inputData
        }
        // Grade restoration is complex as it requires list parsing logic which might reset current work.
        // For now, only supporting Simple Calc restore or just viewing result.
    }
    
    // --- History State ---
    val historyItems: StateFlow<List<HistoryEntity>> = historyDao.getAllHistory()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteHistory(item: HistoryEntity) {
        viewModelScope.launch {
            historyDao.deleteHistoryById(item.id)
        }
    }
    
    fun clearAllHistory() {
        viewModelScope.launch {
            historyDao.clearAllHistory()
        }
    }
}
