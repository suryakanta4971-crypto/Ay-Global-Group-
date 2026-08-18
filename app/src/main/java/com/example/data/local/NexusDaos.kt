package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users LIMIT 1")
    fun getCurrentUser(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: UserEntity)

    @Query("UPDATE users SET tier = :tier WHERE id = :userId")
    suspend fun updateTier(userId: String, tier: String)

    @Query("UPDATE users SET points = points + :pointsAdded WHERE id = :userId")
    suspend fun addPoints(userId: String, pointsAdded: Int)

    @Query("UPDATE users SET role = :role WHERE id = :userId")
    suspend fun updateRole(userId: String, role: String)
}

@Dao
interface FitnessDao {
    @Query("SELECT * FROM fitness_logs ORDER BY timestamp DESC LIMIT 1")
    fun getLatestFitnessLog(): Flow<FitnessLogEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: FitnessLogEntity)

    @Query("UPDATE fitness_logs SET waterMl = waterMl + :amountMl WHERE id = (SELECT id FROM fitness_logs ORDER BY timestamp DESC LIMIT 1)")
    suspend fun addWater(amountMl: Int)

    @Query("UPDATE fitness_logs SET steps = steps + :stepsCount, caloriesBurned = caloriesBurned + :calories WHERE id = (SELECT id FROM fitness_logs ORDER BY timestamp DESC LIMIT 1)")
    suspend fun addStepsAndCalories(stepsCount: Int, calories: Int)
}

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses")
    fun getAllCourses(): Flow<List<CourseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    @Query("UPDATE courses SET isEnrolled = 1 WHERE id = :courseId")
    suspend fun enrollInCourse(courseId: String)

    @Query("UPDATE courses SET completedLessons = completedLessons + 1 WHERE id = :courseId")
    suspend fun advanceLesson(courseId: String)
}

@Dao
interface CommunityDao {
    @Query("SELECT * FROM community_posts ORDER BY timestamp DESC")
    fun getAllPosts(): Flow<List<CommunityPostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: CommunityPostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPosts(posts: List<CommunityPostEntity>)

    @Query("UPDATE community_posts SET likesCount = likesCount + 1, isLiked = 1 WHERE id = :postId")
    suspend fun likePost(postId: String)
}

@Dao
interface JobDao {
    @Query("SELECT * FROM jobs")
    fun getAllJobs(): Flow<List<JobEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobs(jobs: List<JobEntity>)

    @Query("UPDATE jobs SET isApplied = 1 WHERE id = :jobId")
    suspend fun applyJob(jobId: String)
}

@Dao
interface RewardDao {
    @Query("SELECT * FROM rewards")
    fun getAllRewards(): Flow<List<RewardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRewards(rewards: List<RewardEntity>)

    @Query("UPDATE rewards SET isRedeemed = 1 WHERE id = :rewardId")
    suspend fun redeemReward(rewardId: String)
}

@Dao
interface SupportDao {
    @Query("SELECT * FROM support_tickets ORDER BY id DESC")
    fun getAllTickets(): Flow<List<SupportTicketEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: SupportTicketEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTickets(tickets: List<SupportTicketEntity>)
}
