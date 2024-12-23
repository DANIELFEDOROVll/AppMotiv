package com.example.newappmotiv.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DayTask::class, GeneralTasks::class, StoresItem::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDaoTasks(): DaoTask
    abstract fun getDaoGeneralTasks(): DaoGeneralTask
    abstract fun getDaoStore(): DaoStore

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}