package com.devloyde.healthguard.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devloyde.healthguard.models.*

@Database(
    entities = [
        // For Statistics
        GlobalStat::class,
        StatCountries::class,
        StatHistory::class,
        // For News
        RecommendedNews::class,
        LocalNews::class,
        GlobalNews::class,
        CountryNews::class,
        TimeoutCheck::class
    ], version = 1.2.toInt(),
     exportSchema = false
)
abstract class HealthGuardDatabase : RoomDatabase(){
    abstract fun newsDao(): NewsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: HealthGuardDatabase? = null

        fun getDatabase(context: Context): HealthGuardDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HealthGuardDatabase::class.java,
                    "health_guard_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}


