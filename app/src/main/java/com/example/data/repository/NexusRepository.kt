package com.example.data.repository

import com.example.data.local.NexusDatabase
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NexusRepository(private val db: NexusDatabase) {

    // User State
    private val _currentUser = MutableStateFlow(
        User(
            id = "usr_001",
            name = "Alex Rivera",
            email = "alex.rivera@nexusplatform.io",
            phone = "+1 (555) 234-5678",
            role = UserRole.MEMBER,
            memberId = "NX-2026-8849",
            tier = MembershipTier.PRO,
            points = 2450,
            streakDays = 14,
            isVerified = true,
            joinedDate = "Jan 12, 2026"
        )
    )
    val currentUser = _currentUser.asStateFlow()

    // Fitness State
    private val _fitnessData = MutableStateFlow(FitnessData())
    val fitnessData = _fitnessData.asStateFlow()

    private val _workoutPrograms = MutableStateFlow(
        listOf(
            WorkoutProgram("w1", "High Intensity Core Burn", "Cardio & Core", "Intermediate", 25, 320),
            WorkoutProgram("w2", "Full Body Hypertrophy", "Strength", "Advanced", 45, 510),
            WorkoutProgram("w3", "Mindful Mobility & Breathwork", "Recovery", "All Levels", 20, 140),
            WorkoutProgram("w4", "Peak Endurance HIIT", "Cardio", "Advanced", 35, 460)
        )
    )
    val workoutPrograms = _workoutPrograms.asStateFlow()

    // Courses State
    private val _courses = MutableStateFlow(
        listOf(
            Course(
                id = "c1",
                title = "Modern Full-Stack Cloud Architecture",
                instructor = "Dr. Elena Rostova",
                category = "Engineering",
                totalLessons = 12,
                completedLessons = 8,
                rating = 4.9f,
                durationHours = "18.5 hrs",
                level = "Advanced",
                isEnrolled = true,
                lessons = listOf(
                    Lesson("l1", "Microservices & Distributed Events", 22, true, "Deep dive into event-driven design"),
                    Lesson("l2", "PostgreSQL & Prisma Query Optimization", 28, true, "Indexing, query caching, connection pooling"),
                    Lesson("l3", "High-Throughput WebSockets & Live Streaming", 35, true, "Real-time state synchronization architectures"),
                    Lesson("l4", "Edge Compute & Serverless Orchestration", 30, false, "Minimizing TTFB with Cloudflare and WASM")
                )
            ),
            Course(
                id = "c2",
                title = "AI-Driven Product Strategy & Scaling",
                instructor = "Marcus Vance",
                category = "Business",
                totalLessons = 10,
                completedLessons = 4,
                rating = 4.8f,
                durationHours = "12.0 hrs",
                level = "Intermediate",
                isEnrolled = true,
                lessons = listOf(
                    Lesson("l21", "LLM Fine-Tuning for Enterprise", 25, true, "Domain-specific adaptation strategies"),
                    Lesson("l22", "Monetization Models for AI Systems", 30, true, "Usage pricing vs Seat licenses"),
                    Lesson("l23", "User Privacy & Enterprise Compliance", 20, false, "SOC2, HIPAA, and Data sanitization")
                )
            ),
            Course(
                id = "c3",
                title = "Holistic Performance & Longevity Protocol",
                instructor = "Dr. Sophia Chen, MD",
                category = "Health & Wellness",
                totalLessons = 8,
                completedLessons = 0,
                rating = 5.0f,
                durationHours = "9.5 hrs",
                level = "Beginner",
                isEnrolled = false,
                lessons = listOf(
                    Lesson("l31", "Circadian Rhythm Optimization", 18, false, "Light protocols, sleep architecture"),
                    Lesson("l32", "Metabolic Flexibility & Nutrition", 25, false, "Intermittent fasting & macro ratios")
                )
            ),
            Course(
                id = "c4",
                title = "Executive Leadership & High-Output Teams",
                instructor = "David Sterling",
                category = "Leadership",
                totalLessons = 14,
                completedLessons = 0,
                rating = 4.9f,
                durationHours = "15.0 hrs",
                level = "Executive",
                isEnrolled = false,
                lessons = listOf(
                    Lesson("l41", "Radical Candor & Team Velocity", 20, false, "High-trust communication frameworks")
                )
            )
        )
    )
    val courses = _courses.asStateFlow()

    // Live Classes State
    private val _liveClasses = MutableStateFlow(
        listOf(
            LiveClass(
                id = "lc1",
                title = "Live Q&A: Enterprise System Architecture & Scalability",
                instructor = "Dr. Elena Rostova",
                category = "Engineering",
                timeFormatted = "Today at 9:00 PM (Live Now)",
                durationMinutes = 60,
                zoomMeetingId = "849 2039 4920",
                zoomPasscode = "NX8849",
                attendeesCount = 142,
                isLiveNow = true,
                isRegistered = true
            ),
            LiveClass(
                id = "lc2",
                title = "Interactive Breathwork & Vagal Tone Masterclass",
                instructor = "Dr. Sophia Chen",
                category = "Health",
                timeFormatted = "Tomorrow at 7:30 AM",
                durationMinutes = 45,
                zoomMeetingId = "912 3847 1102",
                zoomPasscode = "ZEN2026",
                attendeesCount = 89,
                isLiveNow = false,
                isRegistered = false
            ),
            LiveClass(
                id = "lc3",
                title = "Startup Valuation & VC Pitching Masterclass",
                instructor = "Marcus Vance",
                category = "Business",
                timeFormatted = "Wednesday at 5:00 PM",
                durationMinutes = 75,
                zoomMeetingId = "772 1928 3341",
                zoomPasscode = "VENTURE26",
                attendeesCount = 210,
                isLiveNow = false,
                isRegistered = true
            )
        )
    )
    val liveClasses = _liveClasses.asStateFlow()

    // Community Posts
    private val _communityPosts = MutableStateFlow(
        listOf(
            CommunityPost(
                id = "p1",
                authorName = "Elena Rostova",
                authorRole = "Lead Architect",
                authorTier = "Elite VIP",
                timeAgo = "2h ago",
                content = "Just published the updated benchmarks on our Prisma + PostgreSQL connection pooling setup. We reduced average p99 latency from 140ms to 24ms by utilizing client-level prepared statements!",
                tag = "#Database #PostgreSQL",
                likesCount = 38,
                commentsCount = 6,
                isLiked = true,
                replies = listOf(
                    PostReply("r1", "Alex Rivera", "Member", "Incredible performance gain! Are you using PgBouncer or native Prisma Accelerate?", "1h ago"),
                    PostReply("r2", "Marcus Vance", "Mentor", "Great results! We should review this in tonight's live Zoom session.", "45m ago")
                )
            ),
            CommunityPost(
                id = "p2",
                authorName = "Dr. Sophia Chen",
                authorRole = "Health Director",
                authorTier = "Lifetime",
                timeAgo = "5h ago",
                content = "Reminder for all Nexus members doing the 30-day streak: recovery sleep and consistent hydration are just as important as the workout volume. Track your daily logs in the Fitness tab!",
                tag = "#Longevity #Health",
                likesCount = 54,
                commentsCount = 4,
                isLiked = false,
                replies = listOf(
                    PostReply("r3", "Sarah Jenkins", "Pro Member", "Hit 14 days straight today! Feeling amazing.", "3h ago")
                )
            ),
            CommunityPost(
                id = "p3",
                authorName = "Jordan Lee",
                authorRole = "Founder @ CloudX",
                authorTier = "Pro Member",
                timeAgo = "8h ago",
                content = "Looking for a Senior Kotlin / Jetpack Compose developer to join our team via the Nexus Job Board! Competitive equity + remote. Check the Jobs tab to apply.",
                tag = "#Hiring #Careers",
                likesCount = 29,
                commentsCount = 8,
                isLiked = false
            )
        )
    )
    val communityPosts = _communityPosts.asStateFlow()

    // Business Services
    private val _businessServices = MutableStateFlow(
        listOf(
            BusinessService("bs1", "Cloud Infrastructure Audit & Optimization", "Apex Cloud Labs", "Engineering", "$1,499", 4.9f, 28, "Comprehensive architecture and cost-reduction audit for PostgreSQL and Kubernetes setups."),
            BusinessService("bs2", "Enterprise AI Agent Custom Integration", "Nexus Cognitive", "AI & ML", "$2,800", 5.0f, 41, "End-to-end deployment of secure, custom LLM reasoning agents for your internal data."),
            BusinessService("bs3", "Executive Fitness & Nutrition Coaching", "Metabolic Sync", "Health & Wellness", "$399/mo", 4.8f, 65, "1-on-1 weekly biometric consultations with certified longevity specialists."),
            BusinessService("bs4", "Venture Capital Pitch Deck Formulation", "Synergy Growth", "Finance", "$899", 4.9f, 19, "Refined financial modeling and slide narrative review by active angel investors.")
        )
    )
    val businessServices = _businessServices.asStateFlow()

    // Jobs State
    private val _jobs = MutableStateFlow(
        listOf(
            Job("j1", "Senior Android Engineer (Jetpack Compose)", "Apex Cloud Labs", "Remote", "Full-time", "$140,000 - $175,000", "Engineering", listOf("Kotlin", "Jetpack Compose", "Coroutines", "Room"), "1d ago", false, "Looking for a high-craft Android specialist to build responsive multiplatform applications."),
            Job("j2", "Staff Data Architect (PostgreSQL & Prisma)", "Nexus Cognitive", "San Francisco, CA (Hybrid)", "Full-time", "$160,000 - $210,000", "Data", listOf("PostgreSQL", "Prisma", "Distributed Systems"), "2d ago", true, "Lead schema design and distributed caching across multi-tenant database clusters."),
            Job("j3", "Senior AI Solutions Consultant", "Synergy Growth", "Remote", "Contract", "$90 - $130 / hr", "AI / Strategy", listOf("Gemini API", "Python", "RAG", "Enterprise AI"), "3d ago", false, "Consult with enterprise clients on deploying cutting-edge LLM agent workflows."),
            Job("j4", "Lead UI/UX Systems Designer", "Verve Health", "New York, NY", "Full-time", "$130,000 - $160,000", "Design", listOf("Figma", "Design Systems", "Material 3"), "4d ago", false, "Craft world-class digital experiences across health, fitness and educational platforms.")
        )
    )
    val jobs = _jobs.asStateFlow()

    // Rewards State
    private val _rewards = MutableStateFlow(
        listOf(
            RewardItem("r1", "$50 Cloud Credit Voucher", 1200, "Tech Perk", "NEXUS-CLOUD-50", "Redeem towards AWS, GCP, or Nexus Hosting credits.", false),
            RewardItem("r2", "1-Month Pro Membership Extension", 1800, "Membership", "PRO-EXT-30D", "Add 30 days of VIP Pro access to your current plan.", false),
            RewardItem("r3", "1-on-1 Mentor Strategy Session", 3000, "Coaching", "MENTOR-1ON1-VIP", "Book a private 45-minute live consultation with top industry leads.", false),
            RewardItem("r4", "Nexus Titanium Branded Swag Box", 4500, "Merchandise", "SWAG-BOX-2026", "Includes insulated thermos, embroidered hoodie, and tech pouch.", false)
        )
    )
    val rewards = _rewards.asStateFlow()

    // Support Tickets State
    private val _supportTickets = MutableStateFlow(
        listOf(
            SupportTicket(
                id = "st1",
                ticketNumber = "TICK-8841",
                subject = "Question regarding Zoom Live Class recording access",
                category = "Live Classes",
                priority = "Medium",
                status = "In Progress",
                createdAt = "Aug 15, 2026",
                messages = listOf(
                    TicketMessage("m1", "Alex Rivera", "Where can I find the recorded replay for yesterday's Architecture session?", "Aug 15, 10:20 AM", false),
                    TicketMessage("m2", "Nexus Support", "Hello Alex! Class replays are automatically processed and posted to your Courses tab within 4 hours.", "Aug 15, 11:05 AM", true)
                )
            ),
            SupportTicket(
                id = "st2",
                ticketNumber = "TICK-8720",
                subject = "Invoice receipt for Annual Pro Subscription",
                category = "Billing",
                priority = "Low",
                status = "Resolved",
                createdAt = "Aug 10, 2026",
                messages = listOf(
                    TicketMessage("m3", "Alex Rivera", "Need PDF copy of my latest invoice for company expense reimbursement.", "Aug 10, 02:15 PM", false),
                    TicketMessage("m4", "Nexus Support", "Invoice INV-NX-2026-9281 has been generated and sent to your email.", "Aug 10, 02:30 PM", true)
                )
            )
        )
    )
    val supportTickets = _supportTickets.asStateFlow()

    // Invoices State
    private val _invoices = MutableStateFlow(
        listOf(
            Invoice("inv1", "INV-NX-2026-9281", "Aug 12, 2026", "$29.00", "Pro Membership (Monthly)", "Credit Card ending 4242"),
            Invoice("inv2", "INV-NX-2026-8190", "Jul 12, 2026", "$29.00", "Pro Membership (Monthly)", "UPI (alex@upi)"),
            Invoice("inv3", "INV-NX-2026-7023", "Jun 12, 2026", "$29.00", "Pro Membership (Monthly)", "Credit Card ending 4242")
        )
    )
    val invoices = _invoices.asStateFlow()

    // Admin Users List State
    private val _adminUsers = MutableStateFlow(
        listOf(
            User("u1", "Alex Rivera", "alex.rivera@nexusplatform.io", "+1 (555) 234-5678", UserRole.MEMBER, "NX-2026-8849", MembershipTier.PRO, 2450, 14, true, "Jan 12, 2026"),
            User("u2", "Dr. Elena Rostova", "elena@nexusplatform.io", "+1 (555) 987-6543", UserRole.INSTRUCTOR, "NX-2026-1002", MembershipTier.LIFETIME, 8900, 45, true, "Dec 01, 2025"),
            User("u3", "Marcus Vance", "marcus@synergy.io", "+1 (555) 456-7890", UserRole.ENTERPRISE, "NX-2026-3021", MembershipTier.ELITE, 5100, 28, true, "Jan 05, 2026"),
            User("u4", "Sarah Jenkins", "sarah.j@gmail.com", "+1 (555) 321-6549", UserRole.MEMBER, "NX-2026-4491", MembershipTier.FREE, 650, 5, true, "Feb 02, 2026"),
            User("u5", "Admin Root", "admin@nexusplatform.io", "+1 (555) 000-0001", UserRole.ADMIN, "NX-2026-0001", MembershipTier.LIFETIME, 15000, 90, true, "Nov 15, 2025")
        )
    )
    val adminUsers = _adminUsers.asStateFlow()

    // User Operations
    fun updateUser(name: String, email: String, phone: String) {
        _currentUser.update { it.copy(name = name, email = email, phone = phone) }
    }

    fun switchRole(role: UserRole) {
        _currentUser.update { it.copy(role = role) }
    }

    fun upgradeTier(tier: MembershipTier) {
        _currentUser.update { it.copy(tier = tier, points = it.points + 1000) }
    }

    fun addPoints(amount: Int) {
        _currentUser.update { it.copy(points = it.points + amount) }
    }

    // Fitness Operations
    fun logWater(ml: Int) {
        _fitnessData.update { it.copy(waterMl = it.waterMl + ml) }
        addPoints(10)
    }

    fun logSteps(stepsCount: Int, calories: Int) {
        _fitnessData.update { it.copy(steps = it.steps + stepsCount, caloriesBurned = it.caloriesBurned + calories) }
        addPoints(25)
    }

    fun updateWeight(newWeight: Float) {
        _fitnessData.update { it.copy(currentWeightKg = newWeight) }
    }

    fun toggleWorkout(workoutId: String) {
        _workoutPrograms.update { list ->
            list.map { if (it.id == workoutId) it.copy(isCompleted = !it.isCompleted) else it }
        }
        addPoints(50)
    }

    // Courses Operations
    fun enrollCourse(courseId: String) {
        _courses.update { list ->
            list.map { if (it.id == courseId) it.copy(isEnrolled = true) else it }
        }
        addPoints(100)
    }

    fun advanceCourseLesson(courseId: String) {
        _courses.update { list ->
            list.map {
                if (it.id == courseId) {
                    val nextCompleted = (it.completedLessons + 1).coerceAtMost(it.totalLessons)
                    val earnedCert = nextCompleted == it.totalLessons
                    it.copy(completedLessons = nextCompleted, certificateEarned = earnedCert)
                } else it
            }
        }
        addPoints(40)
    }

    // Live Classes Operations
    fun registerForLiveClass(classId: String) {
        _liveClasses.update { list ->
            list.map { if (it.id == classId) it.copy(isRegistered = !it.isRegistered) else it }
        }
    }

    // Community Operations
    fun toggleLikePost(postId: String) {
        _communityPosts.update { list ->
            list.map {
                if (it.id == postId) {
                    val newLiked = !it.isLiked
                    val newCount = if (newLiked) it.likesCount + 1 else (it.likesCount - 1).coerceAtLeast(0)
                    it.copy(isLiked = newLiked, likesCount = newCount)
                } else it
            }
        }
    }

    fun addPost(content: String, tag: String) {
        val user = _currentUser.value
        val newPost = CommunityPost(
            id = "p_${System.currentTimeMillis()}",
            authorName = user.name,
            authorRole = user.role.name,
            authorTier = user.tier.displayName,
            timeAgo = "Just now",
            content = content,
            tag = tag,
            likesCount = 0,
            commentsCount = 0,
            isLiked = false
        )
        _communityPosts.update { listOf(newPost) + it }
        addPoints(30)
    }

    fun addComment(postId: String, text: String) {
        val user = _currentUser.value
        val reply = PostReply("r_${System.currentTimeMillis()}", user.name, user.role.name, text, "Just now")
        _communityPosts.update { list ->
            list.map {
                if (it.id == postId) it.copy(commentsCount = it.commentsCount + 1, replies = it.replies + reply) else it
            }
        }
        addPoints(15)
    }

    // Jobs Operations
    fun applyJob(jobId: String) {
        _jobs.update { list ->
            list.map { if (it.id == jobId) it.copy(isApplied = true) else it }
        }
    }

    // Rewards Operations
    fun redeemReward(rewardId: String): Boolean {
        val reward = _rewards.value.find { it.id == rewardId } ?: return false
        if (_currentUser.value.points >= reward.pointsCost) {
            _currentUser.update { it.copy(points = it.points - reward.pointsCost) }
            _rewards.update { list ->
                list.map { if (it.id == rewardId) it.copy(isRedeemed = true) else it }
            }
            return true
        }
        return false
    }

    // Support Operations
    fun createSupportTicket(subject: String, category: String, priority: String, message: String) {
        val ticketNum = "TICK-${(8800..9999).random()}"
        val newTicket = SupportTicket(
            id = "st_${System.currentTimeMillis()}",
            ticketNumber = ticketNum,
            subject = subject,
            category = category,
            priority = priority,
            status = "Open",
            createdAt = "Today",
            messages = listOf(TicketMessage("m_${System.currentTimeMillis()}", _currentUser.value.name, message, "Just now", false))
        )
        _supportTickets.update { listOf(newTicket) + it }
    }

    // Admin Operations
    fun updateUserRoleInAdmin(userId: String, newRole: UserRole) {
        _adminUsers.update { list ->
            list.map { if (it.id == userId) it.copy(role = newRole) else it }
        }
        if (_currentUser.value.id == userId) {
            _currentUser.update { it.copy(role = newRole) }
        }
    }
}
