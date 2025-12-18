package com.android.studentgradecalculator.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.studentgradecalculator.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "Dashboard",
                        fontWeight = FontWeight.Bold
                    ) 
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                "Calculators",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Row 1: GPA and CGPA Calculators
            Row(modifier = Modifier.fillMaxWidth()) {
                HomeCard(
                    title = "GPA Calculator",
                    description = "Calculate grades & GPA",
                    icon = Icons.Default.School,
                    color = MaterialTheme.colorScheme.primary,
                    onClick = { navController.navigate(Screen.GradeCalculator.route) },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                HomeCard(
                    title = "CGPA Calculator",
                    description = "Calculate cumulative GPA",
                    icon = Icons.AutoMirrored.Filled.TrendingUp,
                    color = MaterialTheme.colorScheme.tertiary,
                    onClick = { navController.navigate(Screen.CGPACalculator.route) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            
            // Row 2: Basic Calculator (full width)
            HomeCard(
                title = "Basic Calculator",
                description = "Quick math operations",
                icon = Icons.Default.Calculate,
                color = MaterialTheme.colorScheme.secondary,
                onClick = { navController.navigate(Screen.SimpleCalculator.route) },
                modifier = Modifier.fillMaxWidth(),
                height = 140.dp
            )

            Spacer(modifier = Modifier.height(12.dp))
            
            // Student Manager
            HomeCard(
                title = "Student Manager",
                description = "Track student records",
                icon = Icons.Default.School,
                color = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                onClick = { navController.navigate(Screen.StudentManager.route) },
                modifier = Modifier.fillMaxWidth(),
                height = 140.dp
            )

            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                "Quick Access",
                 style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            Row(modifier = Modifier.fillMaxWidth()) {
                 HomeCard(
                    title = "History",
                    icon = Icons.Default.History,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = { navController.navigate(Screen.History.route) },
                    modifier = Modifier.weight(1f),
                     height = 120.dp
                )
                Spacer(modifier = Modifier.width(12.dp))
                HomeCard(
                    title = "Settings",
                    icon = Icons.Default.Settings,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = { navController.navigate(Screen.Settings.route) },
                    modifier = Modifier.weight(1f),
                    height = 120.dp
                )
            }
        }
    }
}

@Composable
fun HomeCard(
    title: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    description: String = "",
    contentColor: Color = Color.White,
    height: Dp = 160.dp
) {
    Card(
        modifier = modifier
            .height(height)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            color,
                            color.copy(alpha = 0.85f)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            // Background decorative icon - adjusted to prevent clipping
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor.copy(alpha = 0.15f),
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 10.dp, y = 10.dp)
            )
            
            Column(modifier = Modifier.align(Alignment.TopStart)) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = contentColor,
                    lineHeight = 24.sp
                )
                if (description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = contentColor.copy(alpha = 0.9f),
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
