package com.android.studentgradecalculator.ui.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.studentgradecalculator.ui.home.HomeCard
import com.android.studentgradecalculator.ui.navigation.Screen

@Composable
fun CalculatorsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Calculators",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        HomeCard(
            title = "Basic Calculator",
            icon = Icons.Default.Edit,
            color = MaterialTheme.colorScheme.primaryContainer,
            onClick = { navController.navigate(Screen.SimpleCalculator.route) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        HomeCard(
            title = "Grade Calculator",
            icon = Icons.Default.Star,
            color = MaterialTheme.colorScheme.secondaryContainer,
            onClick = { navController.navigate(Screen.GradeCalculator.route) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        HomeCard(
            title = "Student Manager",
            icon = Icons.Default.Person,
            color = MaterialTheme.colorScheme.tertiaryContainer,
            onClick = { navController.navigate(Screen.StudentManager.route) }
        )
    }
}
