package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.DigitalMemberCard
import com.example.ui.components.StatMetricCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.ScreenRoute

@Composable
fun DashboardScreen(
    user: User,
    fitnessData: FitnessData,
    courses: List<Course>,
    liveClasses: List<LiveClass>,
    communityPosts: List<CommunityPost>,
    onNavigate: (ScreenRoute) -> Unit,
    onJoinLiveClass: (LiveClass) -> Unit,
    onLogWater: () -> Unit,
    onSpinWheel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val enrolledCourses = courses.filter { it.isEnrolled }
    val activeLive = liveClasses.firstOrNull { it.isLiveNow } ?: liveClasses.firstOrNull()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 4.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Hero AI Assistant Card (Immersive UI Glowing Gradient)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(ScreenRoute.AiAssistant) }
                    .testTag("zenith_ai_hero_card")
            ) {
                // Ambient glow layer
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    ImmersivePrimary.copy(alpha = 0.25f),
                                    ImmersiveCyan.copy(alpha = 0.25f)
                                )
                            )
                        )
                        .padding(1.dp)
                )

                // Main card surface
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = ImmersiveSurface),
                    border = BorderStroke(1.dp, ImmersiveBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "ZENITH AI ASSISTANT",
                                    color = ImmersiveCyan,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.5.sp,
                                        fontSize = 10.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "How can I assist your health and growth goals today?",
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        lineHeight = 22.sp
                                    )
                                )
                            }
                            Surface(
                                shape = CircleShape,
                                color = ImmersiveSurfaceVariant,
                                modifier = Modifier.size(34.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Bolt,
                                        contentDescription = "AI",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Real-time AI status bar
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = ImmersiveSurfaceVariant,
                            border = BorderStroke(1.dp, ImmersiveBorder.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(ImmersiveCyan)
                                        .shadow(6.dp, CircleShape, spotColor = ImmersiveCyan)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Analyzing active workout & learning milestones...",
                                    color = ImmersiveTextSecondary,
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }

        // Ecosystem Modules Grid (4-Column Immersive Tiles)
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ECOSYSTEM MODULES",
                        color = ImmersiveTextMuted,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp,
                            fontSize = 11.sp
                        )
                    )
                    Text(
                        text = "EXPLORE ALL",
                        color = ImmersivePrimaryGlow,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        ),
                        modifier = Modifier.clickable { onNavigate(ScreenRoute.Courses) }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 4x2 Grid of Module Tiles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ImmersiveModuleTile(
                        label = "Health",
                        symbol = "H",
                        accentColor = Color(0xFF3B82F6),
                        onClick = { onNavigate(ScreenRoute.HealthFitness) },
                        modifier = Modifier.weight(1f)
                    )
                    ImmersiveModuleTile(
                        label = "Fitness",
                        symbol = "F",
                        accentColor = Color(0xFF10B981),
                        onClick = { onNavigate(ScreenRoute.HealthFitness) },
                        modifier = Modifier.weight(1f)
                    )
                    ImmersiveModuleTile(
                        label = "Courses",
                        symbol = "C",
                        accentColor = Color(0xFF8B5CF6),
                        onClick = { onNavigate(ScreenRoute.Courses) },
                        modifier = Modifier.weight(1f)
                    )
                    ImmersiveModuleTile(
                        label = "Live",
                        symbol = "L",
                        accentColor = Color(0xFFEF4444),
                        onClick = { onNavigate(ScreenRoute.LiveClasses) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ImmersiveModuleTile(
                        label = "Jobs",
                        symbol = "J",
                        accentColor = Color(0xFFF97316),
                        onClick = { onNavigate(ScreenRoute.Jobs) },
                        modifier = Modifier.weight(1f)
                    )
                    ImmersiveModuleTile(
                        label = "Rewards",
                        symbol = "R",
                        accentColor = Color(0xFFEAB308),
                        onClick = { onNavigate(ScreenRoute.Rewards) },
                        modifier = Modifier.weight(1f)
                    )
                    ImmersiveModuleTile(
                        label = "Business",
                        symbol = "B",
                        accentColor = Color(0xFF06B6D4),
                        onClick = { onNavigate(ScreenRoute.BusinessHub) },
                        modifier = Modifier.weight(1f)
                    )
                    ImmersiveModuleTile(
                        label = "Admin",
                        symbol = "A",
                        accentColor = Color(0xFF94A3B8),
                        onClick = { onNavigate(ScreenRoute.Admin) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // "Happening Now" High-Contrast Electric Blue Live Banner
        if (activeLive != null) {
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = ImmersivePrimary),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(16.dp, RoundedCornerShape(24.dp), spotColor = ImmersivePrimary)
                        .testTag("happening_now_live_banner")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(7.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (activeLive.isLiveNow) "HAPPENING NOW" else "NEXT MASTERCLASS",
                                    color = Color.White.copy(alpha = 0.85f),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.2.sp,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = activeLive.title,
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "with ${activeLive.instructor} • ${activeLive.attendeesCount} attending",
                                color = Color.White.copy(alpha = 0.7f),
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Button(
                            onClick = { onJoinLiveClass(activeLive) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = ImmersivePrimary
                            ),
                            shape = RoundedCornerShape(16.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                            modifier = Modifier.testTag("join_live_now_btn")
                        ) {
                            Text(
                                text = "JOIN LIVE",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }
        }

        // Digital Member Pass Card
        item {
            Column {
                Text(
                    text = "DIGITAL PASS & CREDENTIAL",
                    color = ImmersiveTextMuted,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp,
                        fontSize = 11.sp
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                DigitalMemberCard(
                    user = user,
                    onUpgradeClick = { onNavigate(ScreenRoute.MembershipPlans) }
                )
            }
        }

        // Daily Vitals Summary
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "DAILY VITALS",
                        color = ImmersiveTextMuted,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp,
                            fontSize = 11.sp
                        )
                    )
                    TextButton(onClick = { onNavigate(ScreenRoute.HealthFitness) }) {
                        Text("Detailed Log", color = ImmersiveCyan, fontSize = 12.sp)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatMetricCard(
                        title = "Daily Steps",
                        value = "${fitnessData.steps}",
                        subtitle = "Target: ${fitnessData.targetSteps}",
                        icon = Icons.Default.DirectionsWalk,
                        accentColor = ImmersiveCyan,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(ScreenRoute.HealthFitness) }
                    )
                    StatMetricCard(
                        title = "Active Burn",
                        value = "${fitnessData.caloriesBurned} kcal",
                        subtitle = "Target: ${fitnessData.targetCalories} kcal",
                        icon = Icons.Default.LocalFireDepartment,
                        accentColor = NexusRose,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(ScreenRoute.HealthFitness) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatMetricCard(
                        title = "Hydration",
                        value = "${fitnessData.waterMl} ml",
                        subtitle = "Target: ${fitnessData.targetWaterMl} ml",
                        icon = Icons.Default.WaterDrop,
                        accentColor = Color(0xFF38BDF8),
                        modifier = Modifier.weight(1f),
                        onClick = onLogWater
                    )
                    StatMetricCard(
                        title = "Reward Points",
                        value = "${user.points} pts",
                        subtitle = "Spin Available!",
                        icon = Icons.Default.Stars,
                        accentColor = NexusAmber,
                        modifier = Modifier.weight(1f),
                        onClick = onSpinWheel
                    )
                }
            }
        }
    }
}

@Composable
fun ImmersiveModuleTile(
    label: String,
    symbol: String,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = ImmersiveSurface,
            border = BorderStroke(1.dp, ImmersiveBorder),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .testTag("module_tile_${label.lowercase()}")
        ) {
            Box(contentAlignment = Alignment.Center) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = accentColor.copy(alpha = 0.12f),
                    modifier = Modifier.size(28.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = symbol,
                            color = accentColor,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            color = ImmersiveTextSecondary,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
