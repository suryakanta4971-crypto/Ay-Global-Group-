package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import com.example.data.model.MembershipPlan
import com.example.data.model.MembershipTier
import com.example.data.model.User
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MembershipPlansScreen(
    currentUser: User,
    onSelectPlan: (MembershipPlan, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var billingCycle by remember { mutableStateOf("Monthly") } // Monthly, Annual, Lifetime

    val plans = listOf(
        MembershipPlan(
            tier = MembershipTier.FREE,
            title = "Free Community",
            monthlyPrice = "$0",
            annualPrice = "$0",
            lifetimePrice = "$0",
            description = "Essential platform access for beginners and casual enthusiasts.",
            features = listOf(
                "Access to Community Discussions",
                "Basic Daily Step & Water Tracker",
                "Preview 3 Academy Introductory Courses",
                "Job Board Browsing",
                "Standard Email Support"
            )
        ),
        MembershipPlan(
            tier = MembershipTier.PRO,
            title = "Pro Member",
            monthlyPrice = "$29/mo",
            annualPrice = "$290/yr",
            lifetimePrice = "$499 once",
            description = "Complete ecosystem access with live classes and AI coaching.",
            features = listOf(
                "Unlimited Live Zoom Masterclasses",
                "Full Academy Course Catalog + Certificates",
                "Gemini AI Assistant with Unlimited Prompts",
                "Digital VIP Member ID Pass",
                "1-Tap Job Applications & Resume Builder",
                "Monthly 1,000 Reward Bonus Points"
            ),
            isPopular = true
        ),
        MembershipPlan(
            tier = MembershipTier.ELITE,
            title = "Elite VIP",
            monthlyPrice = "$79/mo",
            annualPrice = "$790/yr",
            lifetimePrice = "$999 once",
            description = "Priority masterclasses, 1-on-1 mentor booking and business tools.",
            features = listOf(
                "Everything in Pro Member",
                "1-on-1 Monthly Video Mentorship Sessions",
                "Business Invoicing Tool & Vendor Marketplace",
                "Private Elite Founder Discussion Channel",
                "Early Access to All Beta Features",
                "Priority 24/7 Concierge Support"
            )
        ),
        MembershipPlan(
            tier = MembershipTier.LIFETIME,
            title = "Lifetime Founder",
            monthlyPrice = "$599",
            annualPrice = "$599",
            lifetimePrice = "$599 once",
            description = "Pay once, access the entire Nexus platform forever. No subscriptions.",
            features = listOf(
                "Permanent Lifetime Access to Everything",
                "Exclusive Gold Holographic Member Pass",
                "Founder Badge on Community & Live Classes",
                "Direct Quarterly Advisory Call with Founders",
                "Free Access to all future additions & courses"
            )
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Flexible Membership Plans",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Black),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Unlock live zoom classrooms, career acceleration, AI intelligence, and premium vendor tools.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Billing Cycle Toggle Row
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    SegmentedButton(
                        selected = billingCycle == "Monthly",
                        onClick = { billingCycle = "Monthly" },
                        shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3)
                    ) {
                        Text("Monthly")
                    }
                    SegmentedButton(
                        selected = billingCycle == "Annual",
                        onClick = { billingCycle = "Annual" },
                        shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3)
                    ) {
                        Text("Annual (-20%)")
                    }
                    SegmentedButton(
                        selected = billingCycle == "Lifetime",
                        onClick = { billingCycle = "Lifetime" },
                        shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3)
                    ) {
                        Text("Lifetime VIP")
                    }
                }
            }
        }

        // Plan Cards
        items(plans) { plan ->
            val isCurrentPlan = currentUser.tier == plan.tier
            val price = when (billingCycle) {
                "Monthly" -> plan.monthlyPrice
                "Annual" -> plan.annualPrice
                else -> plan.lifetimePrice
            }

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (plan.isPopular) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f) else MaterialTheme.colorScheme.surface
                ),
                border = if (plan.isPopular) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                elevation = CardDefaults.cardElevation(defaultElevation = if (plan.isPopular) 6.dp else 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("plan_card_${plan.tier.name.lowercase()}")
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = plan.title,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = plan.description,
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }

                        if (plan.isPopular) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = NexusIndigo
                            ) {
                                Text(
                                    text = "MOST POPULAR",
                                    color = Color.White,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Black,
                                        fontSize = 9.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = price,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = if (plan.isPopular) NexusIndigo else MaterialTheme.colorScheme.onSurface
                            )
                        )
                        if (price != "$0" && !price.contains("once")) {
                            Text(
                                text = " / billed $billingCycle",
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                                modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(14.dp))

                    // Features list
                    plan.features.forEach { feature ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = if (plan.isPopular) NexusIndigo else NexusEmerald,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = feature,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    if (isCurrentPlan) {
                        OutlinedButton(
                            onClick = {},
                            enabled = false,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.CheckCircle, null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Current Active Plan")
                        }
                    } else {
                        Button(
                            onClick = { onSelectPlan(plan, billingCycle) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (plan.isPopular) NexusIndigo else MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("upgrade_plan_btn_${plan.tier.name.lowercase()}")
                        ) {
                            Text(if (plan.tier == MembershipTier.FREE) "Downgrade to Free" else "Upgrade to ${plan.title}")
                        }
                    }
                }
            }
        }
    }
}
