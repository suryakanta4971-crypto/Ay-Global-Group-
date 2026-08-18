package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.ui.theme.*
import com.example.ui.viewmodel.AuthUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    authState: AuthUiState,
    onModeToggle: () -> Unit,
    onInputChange: (email: String?, phone: String?, name: String?, role: UserRole?) -> Unit,
    onSendOtp: () -> Unit,
    onVerifyOtp: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var otpInput by remember(authState.otpDigits) { mutableStateOf(authState.otpDigits) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Header & Logo
            Surface(
                shape = CircleShape,
                color = NexusIndigo.copy(alpha = 0.15f),
                modifier = Modifier.size(72.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Hub,
                    contentDescription = "Nexus Logo",
                    tint = NexusIndigo,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Nexus Ecosystem",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Unified Intelligence, Health, Learning & Enterprise Hub",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Main Auth Card
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 500.dp)
                    .testTag("auth_main_card")
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    // Mode Switcher (Login / Register)
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                        SegmentedButton(
                            selected = authState.isLoginMode,
                            onClick = { if (!authState.isLoginMode) onModeToggle() },
                            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                        ) {
                            Text("Sign In")
                        }
                        SegmentedButton(
                            selected = !authState.isLoginMode,
                            onClick = { if (authState.isLoginMode) onModeToggle() },
                            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                        ) {
                            Text("Register")
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    if (!authState.isLoginMode) {
                        // Name Input for Registration
                        OutlinedTextField(
                            value = authState.nameInput,
                            onValueChange = { onInputChange(null, null, it, null) },
                            label = { Text("Full Name") },
                            leadingIcon = { Icon(Icons.Default.Person, null) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("auth_name_input")
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Role Selector
                        Text(
                            text = "Account Type & Role",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(
                                UserRole.MEMBER to "Member",
                                UserRole.INSTRUCTOR to "Instructor",
                                UserRole.ENTERPRISE to "Enterprise"
                            ).forEach { (role, label) ->
                                val isSelected = authState.selectedRole == role
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { onInputChange(null, null, null, role) },
                                    label = { Text(label, fontSize = 12.sp) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Email or Phone input
                    OutlinedTextField(
                        value = authState.emailInput,
                        onValueChange = { onInputChange(it, null, null, null) },
                        label = { Text("Email Address") },
                        placeholder = { Text("alex@nexusplatform.io") },
                        leadingIcon = { Icon(Icons.Default.Email, null) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_email_input")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = authState.phoneInput,
                        onValueChange = { onInputChange(null, it, null, null) },
                        label = { Text("Mobile Number (For OTP Verification)") },
                        placeholder = { Text("+1 (555) 234-5678") },
                        leadingIcon = { Icon(Icons.Default.Phone, null) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_phone_input")
                    )

                    AnimatedVisibility(visible = authState.authError != null) {
                        Text(
                            text = authState.authError ?: "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // OTP Architecture Section
                    if (authState.isOtpSent) {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Enter 4-Digit Security OTP",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = NexusEmerald.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = "Code: 8849",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Bold,
                                                color = NexusEmerald
                                            ),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = otpInput,
                                    onValueChange = { if (it.length <= 6) otpInput = it },
                                    placeholder = { Text("8849") },
                                    leadingIcon = { Icon(Icons.Default.Security, null) },
                                    singleLine = true,
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("otp_input_field")
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Button(
                                    onClick = { onVerifyOtp(otpInput) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("verify_otp_button")
                                ) {
                                    if (authState.isLoading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(18.dp),
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            strokeWidth = 2.dp
                                        )
                                    } else {
                                        Text("Verify & Access Dashboard")
                                    }
                                }
                            }
                        }
                    } else {
                        Button(
                            onClick = onSendOtp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("send_otp_button")
                        ) {
                            if (authState.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(Icons.Default.Lock, null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(if (authState.isLoginMode) "Continue with OTP Authentication" else "Create Account & Send OTP")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Quick Demo Login bypass for rapid testing
                    OutlinedButton(
                        onClick = {
                            onVerifyOtp("8849")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("instant_demo_login_button")
                    ) {
                        Icon(Icons.Default.Bolt, null, tint = NexusAmber, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Instant Member Quick-Access (Demo)")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Security Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = NexusEmerald,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "256-Bit Encrypted • Role-Based Access Control • PWA Ready",
                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
        }
    }
}
