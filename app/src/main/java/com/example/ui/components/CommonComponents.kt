package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.integrations.PaymentGatewayArchitecture
import com.example.data.model.MembershipPlan
import com.example.data.model.User
import com.example.data.model.UserRole
import com.example.ui.theme.*

@Composable
fun NexusImmersiveHeader(
    user: User,
    onRoleSwitch: (UserRole) -> Unit,
    onMemberIdClick: () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showRoleMenu by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // User Profile with Glowing Gradient Border
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { showRoleMenu = true }
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.linearGradient(listOf(ImmersivePrimary, ImmersivePrimaryGlow)))
                    .padding(1.5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(14.5.dp))
                        .background(ImmersiveBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1E293B))
                            .border(1.dp, Color(0xFF334155), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user.name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString("").uppercase(),
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "${user.tier.displayName.uppercase()} MEMBER",
                    color = ImmersiveTextMuted,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp,
                        fontSize = 10.sp
                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = user.name,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Switch Role",
                        tint = ImmersiveTextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            DropdownMenu(
                expanded = showRoleMenu,
                onDismissRequest = { showRoleMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Role: Member") },
                    onClick = {
                        onRoleSwitch(UserRole.MEMBER)
                        showRoleMenu = false
                    },
                    leadingIcon = { Icon(Icons.Default.Person, null) }
                )
                DropdownMenuItem(
                    text = { Text("Role: Instructor") },
                    onClick = {
                        onRoleSwitch(UserRole.INSTRUCTOR)
                        showRoleMenu = false
                    },
                    leadingIcon = { Icon(Icons.Default.School, null) }
                )
                DropdownMenuItem(
                    text = { Text("Role: Enterprise") },
                    onClick = {
                        onRoleSwitch(UserRole.ENTERPRISE)
                        showRoleMenu = false
                    },
                    leadingIcon = { Icon(Icons.Default.Business, null) }
                )
                DropdownMenuItem(
                    text = { Text("Role: Admin") },
                    onClick = {
                        onRoleSwitch(UserRole.ADMIN)
                        showRoleMenu = false
                    },
                    leadingIcon = { Icon(Icons.Default.AdminPanelSettings, null) }
                )
            }
        }

        // Action Buttons with Immersive Borders
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            // ID Button
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = ImmersiveSurfaceVariant,
                border = BorderStroke(1.dp, ImmersiveBorder),
                modifier = Modifier
                    .size(44.dp)
                    .clickable { onMemberIdClick() }
                    .testTag("header_id_button")
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "ID",
                        color = ImmersiveTextSecondary,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                    )
                }
            }

            // Notification / Pulse button
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = ImmersiveSurfaceVariant,
                border = BorderStroke(1.dp, ImmersiveBorder),
                modifier = Modifier
                    .size(44.dp)
                    .clickable { onNotificationClick() }
                    .testTag("header_pulse_button")
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(ImmersivePrimary)
                            .shadow(8.dp, CircleShape, spotColor = ImmersivePrimary)
                    )
                }
            }
        }
    }
}

@Composable
fun DigitalMemberCard(
    user: User,
    onUpgradeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradientColors = when (user.tier) {
        com.example.data.model.MembershipTier.FREE -> listOf(Color(0xFF1E293B), Color(0xFF0F172A))
        com.example.data.model.MembershipTier.PRO -> listOf(Color(0xFF0F172A), Color(0xFF1E1B4B), ImmersivePrimary)
        com.example.data.model.MembershipTier.ELITE -> listOf(Color(0xFF0A0B10), Color(0xFF1E1B4B), Color(0xFF3D5AFE), Color(0xFF00E5FF))
        com.example.data.model.MembershipTier.LIFETIME -> listOf(Color(0xFF2A1504), Color(0xFF78350F), Color(0xFFF59E0B))
    }

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("digital_member_card")
    ) {
        Box(
            modifier = Modifier
                .background(Brush.linearGradient(gradientColors))
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Header row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.15f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Hub,
                                contentDescription = "Nexus Logo",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(6.dp)
                                    .size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "NEXUS ECOSYSTEM",
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.5.sp
                                )
                            )
                            Text(
                                text = "OFFICIAL DIGITAL PASS",
                                color = Color.White.copy(alpha = 0.6f),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    letterSpacing = 1.sp
                                )
                            )
                        }
                    }

                    // Tier Badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(user.tier.badgeColorHex).copy(alpha = 0.85f)
                    ) {
                        Text(
                            text = user.tier.displayName.uppercase(),
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Member Name & ID
                Text(
                    text = user.name,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = user.email,
                    color = Color.White.copy(alpha = 0.75f),
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Barcode / QR Simulation & ID Info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "MEMBER ID",
                            color = Color.White.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp)
                        )
                        Text(
                            text = user.memberId,
                            color = ImmersiveCyan,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "JOINED ${user.joinedDate.uppercase()}",
                            color = Color.White.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp)
                        )
                    }

                    // Simulated Smart Barcode Bars
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.12f))
                            .padding(8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            listOf(4, 2, 6, 3, 5, 2, 4, 3, 6, 2, 4, 5, 3, 6, 2).forEach { weight ->
                                Box(
                                    modifier = Modifier
                                        .width(weight.dp)
                                        .height(22.dp)
                                        .background(Color.White.copy(alpha = 0.9f))
                                )
                            }
                        }
                        Text(
                            text = "VERIFIED • SECURE NFC",
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 7.sp),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatMetricCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = ImmersiveSurface),
        border = BorderStroke(1.dp, ImmersiveBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .testTag("stat_card_${title.lowercase().replace(" ", "_")}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall.copy(color = ImmersiveTextMuted),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = accentColor.copy(alpha = 0.12f),
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier
                            .padding(7.dp)
                            .size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = accentColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun PaymentCheckoutDialog(
    plan: MembershipPlan,
    billingCycle: String,
    selectedMethod: PaymentGatewayArchitecture.PaymentMethod,
    onMethodSelect: (PaymentGatewayArchitecture.PaymentMethod) -> Unit,
    isProcessing: Boolean,
    paymentResult: PaymentGatewayArchitecture.PaymentResult?,
    onConfirmPayment: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = ImmersiveSurface),
            border = BorderStroke(1.dp, ImmersiveBorder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("payment_checkout_modal")
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (paymentResult != null && paymentResult.isSuccess) {
                    Surface(
                        shape = CircleShape,
                        color = NexusEmerald.copy(alpha = 0.15f),
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = NexusEmerald,
                            modifier = Modifier
                                .padding(12.dp)
                                .size(40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Payment Confirmed!",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Your membership has been upgraded to ${plan.title}.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = ImmersiveTextSecondary),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceVariant),
                        border = BorderStroke(1.dp, ImmersiveBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Transaction ID", color = ImmersiveTextMuted, style = MaterialTheme.typography.bodySmall)
                                Text(paymentResult.transactionId, color = Color.White, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace))
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Invoice Number", color = ImmersiveTextMuted, style = MaterialTheme.typography.bodySmall)
                                Text(paymentResult.invoiceNumber, color = Color.White, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Amount Paid", color = ImmersiveTextMuted, style = MaterialTheme.typography.bodySmall)
                                Text(paymentResult.amountFormatted, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = NexusEmerald))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = ImmersivePrimary),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("payment_modal_done_button")
                    ) {
                        Text("Return to Ecosystem", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Text(
                        text = "Complete Subscription",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Secure Checkout via Razorpay / Stripe Gateway",
                        style = MaterialTheme.typography.bodySmall.copy(color = ImmersiveTextSecondary)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Plan overview
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceVariant),
                        border = BorderStroke(1.dp, ImmersiveBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = plan.title,
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "$billingCycle Billing Plan",
                                    color = ImmersiveTextSecondary,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Text(
                                text = when (billingCycle) {
                                    "Monthly" -> plan.monthlyPrice
                                    "Annual" -> plan.annualPrice
                                    else -> plan.lifetimePrice
                                },
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    color = ImmersiveCyan
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Select Payment Method",
                        color = Color.White,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    PaymentGatewayArchitecture.PaymentMethod.values().forEach { method ->
                        val isSelected = method == selectedMethod
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) ImmersiveSurfaceVariant else ImmersiveSurface,
                            border = BorderStroke(1.dp, if (isSelected) ImmersivePrimary else ImmersiveBorder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { onMethodSelect(method) }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { onMethodSelect(method) },
                                    colors = RadioButtonDefaults.colors(selectedColor = ImmersivePrimary)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = method.title,
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                                    )
                                    Text(
                                        text = method.subtitle,
                                        style = MaterialTheme.typography.bodySmall.copy(color = ImmersiveTextMuted, fontSize = 11.sp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = onConfirmPayment,
                        enabled = !isProcessing,
                        colors = ButtonDefaults.buttonColors(containerColor = ImmersivePrimary),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("confirm_payment_button")
                    ) {
                        if (isProcessing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Processing via Gateway...", color = Color.White)
                        } else {
                            Icon(Icons.Default.Lock, null, modifier = Modifier.size(18.dp), tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Pay & Activate Membership", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = ImmersiveTextSecondary)
                    }
                }
            }
        }
    }
}
