package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.UserRole
import com.example.ui.components.FloatingWhatsAppButton
import com.example.ui.components.NexusImmersiveHeader
import com.example.ui.components.PaymentCheckoutDialog
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: NexusViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                NexusAppRoot(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NexusAppRoot(viewModel: NexusViewModel) {
    val authState by viewModel.authState.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()
    val fitnessData by viewModel.fitnessData.collectAsState()
    val workoutPrograms by viewModel.workoutPrograms.collectAsState()
    val courses by viewModel.courses.collectAsState()
    val liveClasses by viewModel.liveClasses.collectAsState()
    val communityPosts by viewModel.communityPosts.collectAsState()
    val businessServices by viewModel.businessServices.collectAsState()
    val jobs by viewModel.jobs.collectAsState()
    val rewards by viewModel.rewards.collectAsState()
    val supportTickets by viewModel.supportTickets.collectAsState()
    val invoices by viewModel.invoices.collectAsState()
    val adminUsers by viewModel.adminUsers.collectAsState()

    val paymentState by viewModel.paymentState.collectAsState()
    val liveRoomState by viewModel.liveRoomState.collectAsState()
    val coursePlayerState by viewModel.coursePlayerState.collectAsState()
    val aiMessages by viewModel.aiMessages.collectAsState()
    val isAiThinking by viewModel.isAiThinking.collectAsState()
    val userFeedback by viewModel.userFeedback.collectAsState(initial = null)

    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    // Handle feedback toasts
    LaunchedEffect(userFeedback) {
        userFeedback?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearFeedback()
        }
    }

    if (!authState.isAuthenticated) {
        AuthScreen(
            authState = authState,
            onModeToggle = { viewModel.toggleAuthMode() },
            onInputChange = { e, p, n, r -> viewModel.updateAuthInput(e, p, n, r) },
            onSendOtp = { viewModel.sendOtp() },
            onVerifyOtp = { viewModel.verifyOtpAndLogin(it) }
        )
        return
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = ImmersiveSurface,
                modifier = Modifier.width(300.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 12.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = ImmersivePrimary,
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Hub,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Nexus Ecosystem",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Immersive Edition v3.0",
                                color = ImmersiveTextMuted,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    HorizontalDivider(color = ImmersiveBorder, modifier = Modifier.padding(vertical = 8.dp))

                    val drawerItems = listOf(
                        Triple(ScreenRoute.Dashboard, Icons.Default.Dashboard, "Dashboard"),
                        Triple(ScreenRoute.MemberId, Icons.Default.Badge, "Member ID Pass"),
                        Triple(ScreenRoute.MembershipPlans, Icons.Default.Star, "Membership Plans"),
                        Triple(ScreenRoute.HealthFitness, Icons.Default.FitnessCenter, "Health & Fitness"),
                        Triple(ScreenRoute.Courses, Icons.Default.School, "Academy & Courses"),
                        Triple(ScreenRoute.LiveClasses, Icons.Default.Videocam, "Live Zoom Classes"),
                        Triple(ScreenRoute.AiAssistant, Icons.Default.SmartToy, "Zenith AI Assistant"),
                        Triple(ScreenRoute.Community, Icons.Default.Forum, "Community Network"),
                        Triple(ScreenRoute.BusinessHub, Icons.Default.Storefront, "Business Hub & Invoicing"),
                        Triple(ScreenRoute.Jobs, Icons.Default.Work, "Jobs & Talent"),
                        Triple(ScreenRoute.Rewards, Icons.Default.MilitaryTech, "Rewards & Points"),
                        Triple(ScreenRoute.Support, Icons.Default.SupportAgent, "Support & Help"),
                        Triple(ScreenRoute.Admin, Icons.Default.AdminPanelSettings, "Admin Control Panel")
                    )

                    drawerItems.forEach { (route, icon, label) ->
                        val isSelected = currentScreen == route
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    icon,
                                    contentDescription = null,
                                    tint = if (isSelected) ImmersiveCyan else ImmersiveTextSecondary
                                )
                            },
                            label = {
                                Text(
                                    label,
                                    color = if (isSelected) Color.White else ImmersiveTextSecondary,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            selected = isSelected,
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = ImmersiveSurfaceVariant,
                                unselectedContainerColor = Color.Transparent
                            ),
                            onClick = {
                                viewModel.navigateTo(route)
                                coroutineScope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            containerColor = ImmersiveBackground,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                NexusImmersiveHeader(
                    user = currentUser,
                    onRoleSwitch = { viewModel.switchUserRole(it) },
                    onMemberIdClick = { viewModel.navigateTo(ScreenRoute.MemberId) },
                    onNotificationClick = { viewModel.showFeedback("All systems operational. No unread alerts.") },
                    modifier = Modifier.statusBarsPadding()
                )
            },
            bottomBar = {
                // Immersive Floating Center Bottom Navigation Bar
                Surface(
                    color = ImmersiveSurface.copy(alpha = 0.95f),
                    border = BorderStroke(1.dp, ImmersiveBorder.copy(alpha = 0.6f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Home
                        ImmersiveNavItem(
                            label = "Home",
                            isSelected = currentScreen is ScreenRoute.Dashboard,
                            icon = Icons.Default.Dashboard,
                            onClick = { viewModel.navigateTo(ScreenRoute.Dashboard) }
                        )

                        // Academy
                        ImmersiveNavItem(
                            label = "Academy",
                            isSelected = currentScreen is ScreenRoute.Courses,
                            icon = Icons.Default.School,
                            onClick = { viewModel.navigateTo(ScreenRoute.Courses) }
                        )

                        // Center Zenith Floating Action Button
                        Box(
                            modifier = Modifier
                                .offset(y = (-14).dp)
                                .size(58.dp)
                                .clip(CircleShape)
                                .background(ImmersiveSurface)
                                .border(BorderStroke(1.dp, ImmersiveBorder), CircleShape)
                                .padding(4.dp)
                                .clickable { viewModel.navigateTo(ScreenRoute.AiAssistant) }
                                .testTag("center_zenith_ai_fab"),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .background(
                                        Brush.linearGradient(
                                            listOf(ImmersivePrimary, ImmersiveCyan)
                                        )
                                    )
                                    .shadow(12.dp, CircleShape, spotColor = ImmersivePrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Z",
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Black,
                                        fontSize = 20.sp
                                    )
                                )
                            }
                        }

                        // Network
                        ImmersiveNavItem(
                            label = "Network",
                            isSelected = currentScreen is ScreenRoute.Community,
                            icon = Icons.Default.Forum,
                            onClick = { viewModel.navigateTo(ScreenRoute.Community) }
                        )

                        // More / Drawer
                        ImmersiveNavItem(
                            label = "More",
                            isSelected = false,
                            icon = Icons.Default.GridView,
                            onClick = { coroutineScope.launch { drawerState.open() } }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(ImmersiveBackground)
            ) {
                when (currentScreen) {
                    is ScreenRoute.Dashboard -> DashboardScreen(
                        user = currentUser,
                        fitnessData = fitnessData,
                        courses = courses,
                        liveClasses = liveClasses,
                        communityPosts = communityPosts,
                        onNavigate = { viewModel.navigateTo(it) },
                        onJoinLiveClass = { viewModel.joinLiveZoomRoom(it) },
                        onLogWater = { viewModel.logWaterGlass() },
                        onSpinWheel = { viewModel.spinDailyWheel() }
                    )
                    is ScreenRoute.MemberId -> MemberIdScreen(
                        user = currentUser,
                        onUpgradeClick = { viewModel.navigateTo(ScreenRoute.MembershipPlans) },
                        onSharePass = { viewModel.showFeedback("Digital Member Pass exported to wallet!") }
                    )
                    is ScreenRoute.MembershipPlans -> MembershipPlansScreen(
                        currentUser = currentUser,
                        onSelectPlan = { plan, cycle -> viewModel.openPaymentModal(plan, cycle) }
                    )
                    is ScreenRoute.HealthFitness -> HealthFitnessScreen(
                        fitnessData = fitnessData,
                        workoutPrograms = workoutPrograms,
                        onLogWater = { viewModel.logWaterGlass() },
                        onLogSteps = { viewModel.logQuickSteps() },
                        onToggleWorkout = { viewModel.logQuickWorkout(it) },
                        onUpdateWeight = { viewModel.updateWeightProfile(it) }
                    )
                    is ScreenRoute.Courses -> CoursesScreen(
                        courses = courses,
                        coursePlayerState = coursePlayerState,
                        onEnrollCourse = { viewModel.enrollInCourse(it) },
                        onOpenLessonPlayer = { c, l -> viewModel.openLessonPlayer(c, l) },
                        onClosePlayer = { viewModel.closeLessonPlayer() },
                        onCompleteLesson = { viewModel.completeCurrentLesson() }
                    )
                    is ScreenRoute.LiveClasses -> LiveClassesScreen(
                        user = currentUser,
                        liveClasses = liveClasses,
                        liveRoomState = liveRoomState,
                        onJoinLiveClass = { viewModel.joinLiveZoomRoom(it) },
                        onLeaveRoom = { viewModel.leaveLiveZoomRoom() },
                        onToggleMic = { viewModel.toggleMic() },
                        onToggleCam = { viewModel.toggleCamera() },
                        onToggleHand = { viewModel.toggleHandRaise() },
                        onSendChat = { viewModel.sendLiveRoomChat(it) }
                    )
                    is ScreenRoute.AiAssistant -> AiAssistantScreen(
                        messages = aiMessages,
                        isAiThinking = isAiThinking,
                        onSendMessage = { viewModel.sendAiPrompt(it) }
                    )
                    is ScreenRoute.Community -> CommunityScreen(
                        posts = communityPosts,
                        onToggleLike = { viewModel.toggleLikeCommunityPost(it) },
                        onCreatePost = { c, t -> viewModel.createCommunityPost(c, t) },
                        onAddComment = { id, text -> viewModel.addCommunityComment(id, text) }
                    )
                    is ScreenRoute.BusinessHub -> BusinessHubScreen(
                        services = businessServices,
                        invoices = invoices,
                        onShowFeedback = { viewModel.showFeedback(it) }
                    )
                    is ScreenRoute.Jobs -> JobsScreen(
                        jobs = jobs,
                        onApplyJob = { viewModel.applyToJob(it) }
                    )
                    is ScreenRoute.Rewards -> RewardsScreen(
                        user = currentUser,
                        rewards = rewards,
                        onSpinWheel = { viewModel.spinDailyWheel() },
                        onRedeemReward = { viewModel.redeemRewardItem(it) }
                    )
                    is ScreenRoute.Support -> SupportScreen(
                        tickets = supportTickets,
                        onSubmitTicket = { s, c, p, m -> viewModel.submitSupportTicket(s, c, p, m) }
                    )
                    is ScreenRoute.Admin -> AdminScreen(
                        adminUsers = adminUsers,
                        onUpdateUserRole = { id, r -> viewModel.updateAdminUserRole(id, r) },
                        onShowFeedback = { viewModel.showFeedback(it) }
                    )
                }

                // Floating WhatsApp Contact Widget on Middle-Right Area
                FloatingWhatsAppButton(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .offset(y = 40.dp)
                )

                // Global Payment Checkout Modal
                if (paymentState.isModalOpen && paymentState.selectedPlan != null) {
                    PaymentCheckoutDialog(
                        plan = paymentState.selectedPlan!!,
                        billingCycle = paymentState.billingCycle,
                        selectedMethod = paymentState.selectedMethod,
                        onMethodSelect = { viewModel.setPaymentMethod(it) },
                        isProcessing = paymentState.isProcessing,
                        paymentResult = paymentState.paymentResult,
                        onConfirmPayment = { viewModel.executePayment() },
                        onDismiss = { viewModel.closePaymentModal() }
                    )
                }
            }
        }
    }
}

@Composable
private fun ImmersiveNavItem(
    label: String,
    isSelected: Boolean,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .testTag("nav_item_${label.lowercase()}")
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(ImmersivePrimary)
                    .shadow(8.dp, RoundedCornerShape(6.dp), spotColor = ImmersivePrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Color.White,
                    modifier = Modifier.size(13.dp)
                )
            }
        } else {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = ImmersiveTextMuted,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label.uppercase(),
            color = if (isSelected) Color.White else ImmersiveTextMuted,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Black,
                letterSpacing = (-0.2).sp,
                fontSize = 9.sp
            )
        )
    }
}
