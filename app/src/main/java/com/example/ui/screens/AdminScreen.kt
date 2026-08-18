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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.integrations.DatabasePrismaArchitecture
import com.example.data.model.User
import com.example.data.model.UserRole
import com.example.ui.components.StatMetricCard
import com.example.ui.theme.*

@Composable
fun AdminScreen(
    adminUsers: List<User>,
    onUpdateUserRole: (String, UserRole) -> Unit,
    onShowFeedback: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showSchemaViewer by remember { mutableStateOf(false) }

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
                    text = "System Administration & Infrastructure",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "High-availability cluster controls, role-based access management, and PostgreSQL database telemetry.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
        }

        // High Level Metrics
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatMetricCard(
                    title = "Total Ecosystem Members",
                    value = "24,850",
                    subtitle = "+14% this month",
                    icon = Icons.Default.People,
                    accentColor = NexusIndigo,
                    modifier = Modifier.weight(1f)
                )
                StatMetricCard(
                    title = "Monthly MRR",
                    value = "$148,200",
                    subtitle = "Stripe & Razorpay live",
                    icon = Icons.Default.MonetizationOn,
                    accentColor = NexusEmerald,
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
                    title = "PostgreSQL Cluster",
                    value = "99.99% Uptime",
                    subtitle = "Read Replicas: 4",
                    icon = Icons.Default.Storage,
                    accentColor = NexusCyan,
                    modifier = Modifier.weight(1f),
                    onClick = { showSchemaViewer = !showSchemaViewer }
                )
                StatMetricCard(
                    title = "Live Zoom Sessions",
                    value = "12 Active",
                    subtitle = "Zoom SDK v5.16",
                    icon = Icons.Default.Videocam,
                    accentColor = NexusRose,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Database Schema Explorer Banner
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "PostgreSQL & Prisma Schema",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Relational models for User, Membership, Fitness, Courses, Invoices & Zoom sessions.",
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }
                    Button(
                        onClick = { showSchemaViewer = !showSchemaViewer },
                        modifier = Modifier.testTag("toggle_prisma_schema_btn")
                    ) {
                        Text(if (showSchemaViewer) "Hide Schema" else "View Schema")
                    }
                }
            }
        }

        // Schema viewer code block
        if (showSchemaViewer) {
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "schema.prisma (PostgreSQL Blueprint)",
                                color = NexusCyanLight,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            )
                            IconButton(onClick = { onShowFeedback("Prisma schema copied to clipboard!") }) {
                                Icon(Icons.Default.ContentCopy, "Copy", tint = Color.White)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = DatabasePrismaArchitecture.PRISMA_SCHEMA_SPEC.take(600) + "\n\n// ... and 8 more relational models",
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        }

        // User Management & RBAC Table
        item {
            Text(
                text = "User Directory & Role-Based Access Control (RBAC)",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        items(adminUsers) { u ->
            var roleMenuExpanded by remember { mutableStateOf(false) }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = u.name,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "${u.email} • ID: ${u.memberId}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tier: ${u.tier.displayName}",
                            style = MaterialTheme.typography.labelSmall.copy(color = NexusIndigo, fontWeight = FontWeight.SemiBold)
                        )
                    }

                    // Role Selector dropdown
                    Box {
                        FilledTonalButton(
                            onClick = { roleMenuExpanded = true },
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(u.role.name, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Icon(Icons.Default.ArrowDropDown, null, modifier = Modifier.size(16.dp))
                        }

                        DropdownMenu(
                            expanded = roleMenuExpanded,
                            onDismissRequest = { roleMenuExpanded = false }
                        ) {
                            UserRole.values().forEach { roleOption ->
                                DropdownMenuItem(
                                    text = { Text(roleOption.name) },
                                    onClick = {
                                        onUpdateUserRole(u.id, roleOption)
                                        roleMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
