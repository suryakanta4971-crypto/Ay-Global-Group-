package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.integrations.PaymentGatewayArchitecture
import com.example.data.local.NexusDatabase
import com.example.data.model.*
import com.example.data.repository.GeminiAiRepository
import com.example.data.repository.NexusRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class ScreenRoute(val title: String, val iconTag: String) {
    data object Dashboard : ScreenRoute("Dashboard", "dashboard")
    data object MemberId : ScreenRoute("Member ID", "badge")
    data object MembershipPlans : ScreenRoute("Membership", "star")
    data object HealthFitness : ScreenRoute("Health & Fitness", "fitness")
    data object Courses : ScreenRoute("Academy", "school")
    data object LiveClasses : ScreenRoute("Live Zoom Classes", "video")
    data object AiAssistant : ScreenRoute("AI Assistant", "smart_toy")
    data object Community : ScreenRoute("Community", "forum")
    data object BusinessHub : ScreenRoute("Business Hub", "storefront")
    data object Jobs : ScreenRoute("Jobs & Careers", "work")
    data object Rewards : ScreenRoute("Rewards", "military_tech")
    data object Support : ScreenRoute("Help & Support", "support")
    data object Admin : ScreenRoute("Admin Control", "admin_panel_settings")
}

data class AuthUiState(
    val isAuthenticated: Boolean = true,
    val isLoginMode: Boolean = true,
    val emailInput: String = "",
    val phoneInput: String = "",
    val nameInput: String = "",
    val selectedRole: UserRole = UserRole.MEMBER,
    val isOtpSent: Boolean = false,
    val otpDigits: String = "",
    val otpTimerSeconds: Int = 45,
    val authError: String? = null,
    val isLoading: Boolean = false
)

data class PaymentUiState(
    val isModalOpen: Boolean = false,
    val selectedPlan: MembershipPlan? = null,
    val billingCycle: String = "Monthly", // "Monthly", "Annual", "Lifetime"
    val selectedMethod: PaymentGatewayArchitecture.PaymentMethod = PaymentGatewayArchitecture.PaymentMethod.UPI,
    val isProcessing: Boolean = false,
    val paymentResult: PaymentGatewayArchitecture.PaymentResult? = null
)

data class LiveRoomUiState(
    val isInLiveRoom: Boolean = false,
    val currentClass: LiveClass? = null,
    val isMuted: Boolean = true,
    val isVideoOn: Boolean = true,
    val isHandRaised: Boolean = false,
    val chatMessages: List<ChatMessage> = listOf(
        ChatMessage("c1", "Elena Rostova", "Welcome everyone! We'll start in 2 minutes.", false, "9:00 PM"),
        ChatMessage("c2", "Dr. Sophia Chen", "Excited for tonight's session!", false, "9:01 PM")
    ),
    val currentChatInput: String = ""
)

data class CoursePlayerUiState(
    val isPlayingLesson: Boolean = false,
    val currentCourse: Course? = null,
    val currentLesson: Lesson? = null,
    val userNotes: String = ""
)

class NexusViewModel(application: Application) : AndroidViewModel(application) {

    private val db = NexusDatabase.getDatabase(application)
    private val repository = NexusRepository(db)
    private val aiRepository = GeminiAiRepository()

    // Navigation
    private val _currentScreen = MutableStateFlow<ScreenRoute>(ScreenRoute.Dashboard)
    val currentScreen = _currentScreen.asStateFlow()

    // Data Flows from Repository
    val currentUser = repository.currentUser
    val fitnessData = repository.fitnessData
    val workoutPrograms = repository.workoutPrograms
    val courses = repository.courses
    val liveClasses = repository.liveClasses
    val communityPosts = repository.communityPosts
    val businessServices = repository.businessServices
    val jobs = repository.jobs
    val rewards = repository.rewards
    val supportTickets = repository.supportTickets
    val invoices = repository.invoices
    val adminUsers = repository.adminUsers

    // Auth State
    private val _authState = MutableStateFlow(AuthUiState())
    val authState = _authState.asStateFlow()

    // Payment State
    private val _paymentState = MutableStateFlow(PaymentUiState())
    val paymentState = _paymentState.asStateFlow()

    // Live Room State
    private val _liveRoomState = MutableStateFlow(LiveRoomUiState())
    val liveRoomState = _liveRoomState.asStateFlow()

    // Course Player State
    private val _coursePlayerState = MutableStateFlow(CoursePlayerUiState())
    val coursePlayerState = _coursePlayerState.asStateFlow()

    // AI Chat State
    private val _aiMessages = MutableStateFlow(
        listOf(
            ChatMessage(
                id = "ai_welcome",
                sender = "Nexus Intelligence",
                text = "Hello Alex! I am your unified Nexus AI Assistant. I can help you with personalized health protocols, course learning, business strategies, and career guidance. What would you like to explore today?",
                isUser = false,
                timestamp = "Just now",
                suggestedChips = listOf(
                    "🏋️ Analyze my daily fitness stats",
                    "🎓 Recommend next course module",
                    "💼 SaaS growth & business advice",
                    "🚀 Resume optimization tips"
                )
            )
        )
    )
    val aiMessages = _aiMessages.asStateFlow()

    private val _isAiThinking = MutableStateFlow(false)
    val isAiThinking = _isAiThinking.asStateFlow()

    // Toast/Feedback state
    private val _userFeedback = MutableStateFlow<String?>(null)
    val userFeedback = _userFeedback.asStateFlow()

    fun navigateTo(route: ScreenRoute) {
        _currentScreen.value = route
    }

    fun clearFeedback() {
        _userFeedback.value = null
    }

    fun showFeedback(message: String) {
        _userFeedback.value = message
    }

    // --- Authentication & OTP Handlers ---
    fun toggleAuthMode() {
        _authState.update { it.copy(isLoginMode = !it.isLoginMode, authError = null) }
    }

    fun updateAuthInput(email: String? = null, phone: String? = null, name: String? = null, role: UserRole? = null) {
        _authState.update {
            it.copy(
                emailInput = email ?: it.emailInput,
                phoneInput = phone ?: it.phoneInput,
                nameInput = name ?: it.nameInput,
                selectedRole = role ?: it.selectedRole
            )
        }
    }

    fun sendOtp() {
        if (_authState.value.phoneInput.isBlank() && _authState.value.emailInput.isBlank()) {
            _authState.update { it.copy(authError = "Please provide an email or phone number") }
            return
        }
        _authState.update { it.copy(isLoading = true, authError = null) }
        viewModelScope.launch {
            delay(800)
            _authState.update {
                it.copy(
                    isLoading = false,
                    isOtpSent = true,
                    otpDigits = "8849" // Demo preset for immediate testing
                )
            }
            showFeedback("6-digit verification code sent: 8849")
        }
    }

    fun verifyOtpAndLogin(enteredOtp: String) {
        if (enteredOtp.length < 4) {
            _authState.update { it.copy(authError = "Please enter valid 4-6 digit OTP") }
            return
        }
        _authState.update { it.copy(isLoading = true, authError = null) }
        viewModelScope.launch {
            delay(600)
            val name = if (_authState.value.nameInput.isNotBlank()) _authState.value.nameInput else "Alex Rivera"
            val email = if (_authState.value.emailInput.isNotBlank()) _authState.value.emailInput else "alex.rivera@nexusplatform.io"
            val phone = if (_authState.value.phoneInput.isNotBlank()) _authState.value.phoneInput else "+1 (555) 234-5678"
            val role = _authState.value.selectedRole

            repository.updateUser(name, email, phone)
            repository.switchRole(role)

            _authState.update {
                it.copy(
                    isLoading = false,
                    isAuthenticated = true,
                    isOtpSent = false
                )
            }
            _currentScreen.value = ScreenRoute.Dashboard
            showFeedback("Welcome to Nexus Ecosystem, $name!")
        }
    }

    fun logout() {
        _authState.update { AuthUiState(isAuthenticated = false) }
        _currentScreen.value = ScreenRoute.Dashboard
    }

    // --- Membership & Payments Handlers ---
    fun openPaymentModal(plan: MembershipPlan, billingCycle: String = "Monthly") {
        _paymentState.value = PaymentUiState(
            isModalOpen = true,
            selectedPlan = plan,
            billingCycle = billingCycle
        )
    }

    fun closePaymentModal() {
        _paymentState.update { it.copy(isModalOpen = false, isProcessing = false) }
    }

    fun setPaymentMethod(method: PaymentGatewayArchitecture.PaymentMethod) {
        _paymentState.update { it.copy(selectedMethod = method) }
    }

    fun executePayment() {
        val currentPlan = _paymentState.value.selectedPlan ?: return
        val user = currentUser.value

        _paymentState.update { it.copy(isProcessing = true) }
        viewModelScope.launch {
            val amountCents = when (_paymentState.value.billingCycle) {
                "Monthly" -> 2900L
                "Annual" -> 29000L
                else -> 59900L
            }

            val result = PaymentGatewayArchitecture.processPayment(
                PaymentGatewayArchitecture.PaymentOrderRequest(
                    amountInCents = amountCents,
                    planName = currentPlan.title,
                    userEmail = user.email,
                    userName = user.name,
                    paymentMethod = _paymentState.value.selectedMethod
                )
            )

            repository.upgradeTier(currentPlan.tier)
            _paymentState.update {
                it.copy(isProcessing = false, paymentResult = result)
            }
            showFeedback("Successfully upgraded to ${currentPlan.title}!")
        }
    }

    // --- Health & Fitness Handlers ---
    fun logWaterGlass() {
        repository.logWater(300)
        showFeedback("+300ml Water logged (+10 XP)")
    }

    fun logQuickWorkout(workout: WorkoutProgram) {
        repository.toggleWorkout(workout.id)
        showFeedback("${if (!workout.isCompleted) "Completed" else "Reset"} ${workout.title} (+50 XP)")
    }

    fun logQuickSteps() {
        repository.logSteps(1500, 95)
        showFeedback("+1,500 Steps & 95 kcal logged (+25 XP)")
    }

    fun updateWeightProfile(weight: Float) {
        repository.updateWeight(weight)
        showFeedback("Target weight updated to ${weight}kg")
    }

    // --- Course & Video Academy Handlers ---
    fun enrollInCourse(course: Course) {
        repository.enrollCourse(course.id)
        showFeedback("Enrolled in ${course.title}!")
    }

    fun openLessonPlayer(course: Course, lesson: Lesson) {
        _coursePlayerState.value = CoursePlayerUiState(
            isPlayingLesson = true,
            currentCourse = course,
            currentLesson = lesson,
            userNotes = "Key Takeaway: Architecture resilience requires decoupled state and distributed caches."
        )
    }

    fun closeLessonPlayer() {
        _coursePlayerState.update { it.copy(isPlayingLesson = false) }
    }

    fun completeCurrentLesson() {
        val course = _coursePlayerState.value.currentCourse ?: return
        repository.advanceCourseLesson(course.id)
        showFeedback("Lesson marked complete! +40 XP")
    }

    // --- Live Zoom Classroom Handlers ---
    fun joinLiveZoomRoom(liveClass: LiveClass) {
        _liveRoomState.value = LiveRoomUiState(
            isInLiveRoom = true,
            currentClass = liveClass
        )
    }

    fun leaveLiveZoomRoom() {
        _liveRoomState.update { it.copy(isInLiveRoom = false) }
        showFeedback("Left live classroom session.")
    }

    fun toggleMic() {
        _liveRoomState.update { it.copy(isMuted = !it.isMuted) }
    }

    fun toggleCamera() {
        _liveRoomState.update { it.copy(isVideoOn = !it.isVideoOn) }
    }

    fun toggleHandRaise() {
        val newHand = !_liveRoomState.value.isHandRaised
        _liveRoomState.update { it.copy(isHandRaised = newHand) }
        showFeedback(if (newHand) "Hand raised for instructor" else "Hand lowered")
    }

    fun sendLiveRoomChat(message: String) {
        if (message.isBlank()) return
        val user = currentUser.value
        val newMsg = ChatMessage(
            id = "lr_${System.currentTimeMillis()}",
            sender = user.name,
            text = message,
            isUser = true,
            timestamp = "Just now"
        )
        _liveRoomState.update {
            it.copy(chatMessages = it.chatMessages + newMsg, currentChatInput = "")
        }
    }

    // --- AI Assistant Handlers ---
    fun sendAiPrompt(promptText: String) {
        if (promptText.isBlank()) return
        val userMsg = ChatMessage(
            id = "u_${System.currentTimeMillis()}",
            sender = "You",
            text = promptText,
            isUser = true,
            timestamp = "Just now"
        )

        _aiMessages.update { it + userMsg }
        _isAiThinking.value = true

        viewModelScope.launch {
            val responseText = aiRepository.getAiResponse(promptText)
            val aiMsg = ChatMessage(
                id = "ai_${System.currentTimeMillis()}",
                sender = "Nexus Intelligence",
                text = responseText,
                isUser = false,
                timestamp = "Just now",
                suggestedChips = listOf("💡 Ask for follow-up details", "📊 Generate action plan", "🎯 Set weekly milestone")
            )
            _isAiThinking.value = false
            _aiMessages.update { it + aiMsg }
        }
    }

    // --- Community Handlers ---
    fun toggleLikeCommunityPost(postId: String) {
        repository.toggleLikePost(postId)
    }

    fun createCommunityPost(content: String, tag: String) {
        if (content.isBlank()) return
        repository.addPost(content, tag)
        showFeedback("Post shared with Nexus Community! +30 XP")
    }

    fun addCommunityComment(postId: String, text: String) {
        if (text.isBlank()) return
        repository.addComment(postId, text)
        showFeedback("Comment posted! +15 XP")
    }

    // --- Jobs Handlers ---
    fun applyToJob(job: Job) {
        repository.applyJob(job.id)
        showFeedback("Application submitted for ${job.title} at ${job.company}!")
    }

    // --- Rewards Handlers ---
    fun redeemRewardItem(reward: RewardItem) {
        val success = repository.redeemReward(reward.id)
        if (success) {
            showFeedback("Voucher redeemed! Code: ${reward.discountCode}")
        } else {
            showFeedback("Insufficient points. Complete more daily goals!")
        }
    }

    fun spinDailyWheel() {
        val bonus = (50..250).random()
        repository.addPoints(bonus)
        showFeedback("Daily Spin: You won +$bonus Reward Points!")
    }

    // --- Support Handlers ---
    fun submitSupportTicket(subject: String, category: String, priority: String, message: String) {
        repository.createSupportTicket(subject, category, priority, message)
        showFeedback("Support Ticket submitted successfully! ID generated.")
    }

    // --- Admin Handlers ---
    fun updateAdminUserRole(userId: String, role: UserRole) {
        repository.updateUserRoleInAdmin(userId, role)
        showFeedback("User role updated to ${role.name}")
    }

    fun switchUserRole(role: UserRole) {
        repository.switchRole(role)
        showFeedback("Active view switched to ${role.name}")
    }
}
