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

}