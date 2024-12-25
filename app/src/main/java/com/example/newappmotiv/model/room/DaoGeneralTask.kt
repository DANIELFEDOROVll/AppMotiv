package com.example.newappmotiv.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoGeneralTask {
    @Insert
    suspend fun insertGeneralTask(generalTasks: GeneralTasks)

    @Query("SELECT * FROM generalTasks")
    suspend fun getGeneralTasks(): List<GeneralTasks>

    @Query("SELECT totalSpentTimeInMinutes FROM generalTasks WHERE name = :taskName")
    suspend fun getTotalSpentTime(taskName: String): Int

    @Query("UPDATE generalTasks SET totalSpentTimeInMinutes = :newValue WHERE name = :taskName")
    suspend fun updateTotalSpentMinutes(taskName: String, newValue: Int)


}