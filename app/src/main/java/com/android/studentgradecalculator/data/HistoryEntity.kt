package com.android.studentgradecalculator.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String, // "SIMPLE", "GRADE", "MULTI"
    val inputData: String,
    val result: String,
    val timestamp: Long = System.currentTimeMillis()
)
