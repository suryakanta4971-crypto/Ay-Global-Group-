package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val role: String,
    val memberId: String,
    val tier: String,
    val points: Int,
    val streakDays: Int,
    val isVerified: Boolean,
    val joinedDate: String,
    val avatarUrl: String
)

@Entity(tableName = "fitness_logs")
data class FitnessLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val steps: Int,
    val targetSteps: Int,
    val caloriesBurned: Int,
    val targetCalories: Int,
    val waterMl: Int,
    val targetWaterMl: Int,
    val sleepHours: Float,
    val currentWeightKg: Float,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey val id: String,
    val title: String,
    val instructor: String,
    val category: String,
    val totalLessons: Int,
    val completedLessons: Int,
    val rating: Float,
    val durationHours: String,
    val level: String,
    val isEnrolled: Boolean,
    val certificateEarned: Boolean
)

@Entity(tableName = "community_posts")
data class CommunityPostEntity(
    @PrimaryKey val id: String,
    val authorName: String,
    val authorRole: String,
    val authorTier: String,
    val timeAgo: String,
    val content: String,
    val tag: String,
    val likesCount: Int,
    val commentsCount: Int,
    val isLiked: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "jobs")
data class JobEntity(
    @PrimaryKey val id: String,
    val title: String,
    val company: String,
    val location: String,
    val type: String,
    val salaryRange: String,
    val category: String,
    val tagsCsv: String,
    val postedAgo: String,
    val isApplied: Boolean,
    val description: String
)

@Entity(tableName = "rewards")
data class RewardEntity(
    @PrimaryKey val id: String,
    val title: String,
    val pointsCost: Int,
    val category: String,
    val discountCode: String,
    val description: String,
    val isRedeemed: Boolean
)

@Entity(tableName = "support_tickets")
data class SupportTicketEntity(
    @PrimaryKey val id: String,
    val ticketNumber: String,
    val subject: String,
    val category: String,
    val priority: String,
    val status: String,
    val createdAt: String
)
