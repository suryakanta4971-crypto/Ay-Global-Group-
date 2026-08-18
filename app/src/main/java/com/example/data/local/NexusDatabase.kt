package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserEntity::class,
        FitnessLogEntity::class,
        CourseEntity::class,
        CommunityPostEntity::class,
        JobEntity::class,
        RewardEntity::class,
        SupportTicketEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NexusDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun fitnessDao(): FitnessDao
    abstract fun courseDao(): CourseDao
    abstract fun communityDao(): CommunityDao
    abstract fun jobDao(): JobDao
    abstract fun rewardDao(): RewardDao
    abstract fun supportDao(): SupportDao

    companion object {
        @Volatile
        private var INSTANCE: NexusDatabase? = null

        fun getDatabase(context: Context): NexusDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NexusDatabase::class.java,
                    "nexus_ecosystem.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
