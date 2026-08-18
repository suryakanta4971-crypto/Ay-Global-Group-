package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FitnessData
import com.example.data.model.WorkoutProgram
import com.example.ui.components.StatMetricCard
import com.example.ui.theme.*

@Composable
fun HealthFitnessScreen(
    fitnessData: FitnessData,
    workoutPrograms: List<WorkoutProgram>,
    onLogWater: () -> Unit,
    onLogSteps: () -> Unit,
    onToggleWorkout: (WorkoutProgram) -> Unit,
    onUpdateWeight: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var heightInput by remember { mutableFloatStateOf(fitnessData.heightCm) }
    var weightInput by remember { mutableFloatStateOf(fitnessData.currentWeightKg) }

    // BMI calculation = weight(kg) / (height(m))^2
    val heightMeters = heightInput / 100f
    val bmiValue = if (heightMeters > 0) weightInput / (heightMeters * heightMeters) else 22.4f
    val bmiCategory = when {
        bmiValue < 18.5 -> "Underweight" to NexusCyan
        bmiValue < 25.0 -> "Optimal (Healthy)" to NexusEmerald
        bmiValue < 30.0 -> "Overweight" to NexusAmber
        else -> "Obese" to NexusRose
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Health, Vitals & Fitness Protocol",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Track your daily biometric progress, hydration, metabolic output, and structured exercise regimens.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
        }

        // Daily Metric Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatMetricCard(
                    title = "Daily Steps",
                    value = "${fitnessData.steps}",
                    subtitle = "${(fitnessData.steps.toFloat() / fitnessData.targetSteps * 100).toInt()}% of goal",
                    icon = Icons.Default.DirectionsWalk,
                    accentColor = NexusCyan,
                    modifier = Modifier.weight(1f)
                )
                StatMetricCard(
                    title = "Active Burn",
                    value = "${fitnessData.caloriesBurned} kcal",
                    subtitle = "Goal: ${fitnessData.targetCalories} kcal",
                    icon = Icons.Default.LocalFireDepartment,
                    accentColor = NexusRose,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatMetricCard(
                    title = "Hydration Intake",
                    value = "${fitnessData.waterMl} ml",
                    subtitle = "Goal: ${fitnessData.targetWaterMl} ml",
                    icon = Icons.Default.WaterDrop,
                    accentColor = Color(0xFF38BDF8),
                    modifier = Modifier.weight(1f)
                )
                StatMetricCard(
                    title = "Sleep & Recovery",
                    value = "${fitnessData.sleepHours} hrs",
                    subtitle = "Quality: Optimal",
                    icon = Icons.Default.Bedtime,
                    accentColor = NexusViolet,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Quick Action Logging
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Quick Metric Logging",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onLogWater,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7)),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("log_water_btn")
                        ) {
                            Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("+300ml Water")
                        }
                        Button(
                            onClick = onLogSteps,
                            colors = ButtonDefaults.buttonColors(containerColor = NexusEmerald),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("log_steps_btn")
                        ) {
                            Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("+1.5k Steps")
                        }
                    }
                }
            }
        }

        // BMI & Body Composition Calculator
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Interactive BMI Calculator",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = bmiCategory.second.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = bmiCategory.first,
                                color = bmiCategory.second,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Height: ${heightInput.toInt()} cm", style = MaterialTheme.typography.bodyMedium)
                        Text("Weight: ${"%.1f".format(weightInput)} kg", style = MaterialTheme.typography.bodyMedium)
                    }

                    Slider(
                        value = heightInput,
                        onValueChange = { heightInput = it },
                        valueRange = 140f..210f,
                        steps = 70,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Current BMI Score:", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = "%.1f".format(bmiValue),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Black,
                                color = bmiCategory.second
                            )
                        )
                    }
                }
            }
        }

        // Workout Programs
        item {
            Text(
                text = "Personalized Workout Programs",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        items(workoutPrograms) { program ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (program.isCompleted) NexusEmerald.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (program.isCompleted) NexusEmerald.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleWorkout(program) }
                    .testTag("workout_card_${program.id}")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = program.category,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${program.durationMinutes} mins • ${program.calories} kcal",
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = program.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Checkbox(
                        checked = program.isCompleted,
                        onCheckedChange = { onToggleWorkout(program) }
                    )
                }
            }
        }
    }
}
