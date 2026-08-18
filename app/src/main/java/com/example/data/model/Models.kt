package com.example.data.model

enum class UserRole {
    MEMBER,
    INSTRUCTOR,
    ENTERPRISE,
    ADMIN
}

enum class MembershipTier(val displayName: String, val badgeColorHex: Long) {
    FREE("Free Tier", 0xFF64748B),
    PRO("Pro Member", 0xFF06B6D4),
    ELITE("Elite VIP", 0xFF4F46E5),
    LIFETIME("Lifetime Founder", 0xFFF59E0B)
}

data class User(
    val id: String = "usr_001",
    val name: String = "Alex Rivera",
    val email: String = "alex.rivera@nexusplatform.io",
    val phone: String = "+1 (555) 234-5678",
    val role: UserRole = UserRole.MEMBER,
    val memberId: String = "NX-2026-8849",
    val tier: MembershipTier = MembershipTier.PRO,
    val points: Int = 2450,
    val streakDays: Int = 14,
    val isVerified: Boolean = true,
    val joinedDate: String = "Jan 12, 2026",
    val avatarUrl: String = ""
)

data class FitnessData(
    val steps: Int = 8450,
    val targetSteps: Int = 10000,
    val caloriesBurned: Int = 620,
    val targetCalories: Int = 750,
    val waterMl: Int = 2400,
    val targetWaterMl: Int = 3000,
    val sleepHours: Float = 7.5f,
    val targetSleepHours: Float = 8.0f,
    val currentWeightKg: Float = 68.5f,
    val targetWeightKg: Float = 65.0f,
    val heightCm: Float = 175f,
    val heartRateBpm: Int = 72
)

data class WorkoutProgram(
    val id: String,
    val title: String,
    val category: String,
    val level: String,
    val durationMinutes: Int,
    val calories: Int,
    val isCompleted: Boolean = false
)

data class Lesson(
    val id: String,
    val title: String,
    val durationMinutes: Int,
    val isCompleted: Boolean = false,
    val summary: String
)

data class Course(
    val id: String,
    val title: String,
    val instructor: String,
    val category: String,
    val totalLessons: Int,
    val completedLessons: Int,
    val rating: Float,
    val durationHours: String,
    val level: String,
    val isEnrolled: Boolean = false,
    val certificateEarned: Boolean = false,
    val lessons: List<Lesson> = emptyList()
)

data class LiveClass(
    val id: String,
    val title: String,
    val instructor: String,
    val category: String,
    val timeFormatted: String,
    val durationMinutes: Int,
    val zoomMeetingId: String,
    val zoomPasscode: String,
    val attendeesCount: Int,
    val isLiveNow: Boolean,
    val isRegistered: Boolean = false
)

data class PostReply(
    val id: String,
    val author: String,
    val authorRole: String,
    val text: String,
    val timeAgo: String
)

data class CommunityPost(
    val id: String,
    val authorName: String,
    val authorRole: String,
    val authorTier: String,
    val timeAgo: String,
    val content: String,
    val tag: String,
    val likesCount: Int,
    val commentsCount: Int,
    val isLiked: Boolean = false,
    val replies: List<PostReply> = emptyList()
)

data class Job(
    val id: String,
    val title: String,
    val company: String,
    val location: String,
    val type: String, // Remote, Full-time, Contract
    val salaryRange: String,
    val category: String,
    val tags: List<String>,
    val postedAgo: String,
    val isApplied: Boolean = false,
    val description: String
)

data class BusinessService(
    val id: String,
    val name: String,
    val provider: String,
    val category: String,
    val price: String,
    val rating: Float,
    val reviewsCount: Int,
    val description: String,
    val isVerifiedVendor: Boolean = true
)

data class RewardItem(
    val id: String,
    val title: String,
    val pointsCost: Int,
    val category: String,
    val discountCode: String,
    val description: String,
    val isRedeemed: Boolean = false
)

data class TicketMessage(
    val id: String,
    val sender: String,
    val text: String,
    val time: String,
    val isAdmin: Boolean
)

data class SupportTicket(
    val id: String,
    val ticketNumber: String,
    val subject: String,
    val category: String,
    val priority: String, // Low, Medium, High, Urgent
    val status: String, // Open, In Progress, Resolved
    val createdAt: String,
    val messages: List<TicketMessage> = emptyList()
)

data class MembershipPlan(
    val tier: MembershipTier,
    val title: String,
    val monthlyPrice: String,
    val annualPrice: String,
    val lifetimePrice: String,
    val description: String,
    val features: List<String>,
    val isPopular: Boolean = false
)

data class ChatMessage(
    val id: String,
    val sender: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: String,
    val suggestedChips: List<String> = emptyList()
)

data class Invoice(
    val id: String,
    val invoiceNumber: String,
    val date: String,
    val amount: String,
    val description: String,
    val paymentMethod: String,
    val status: String = "PAID"
)

data class AdminMetric(
    val totalUsers: Int = 18450,
    val activeSubscribers: Int = 6240,
    val monthlyRevenue: String = "$148,920",
    val liveClassesConducted: Int = 342,
    val systemUptime: String = "99.98%",
    val serverResponseMs: Int = 42
)
