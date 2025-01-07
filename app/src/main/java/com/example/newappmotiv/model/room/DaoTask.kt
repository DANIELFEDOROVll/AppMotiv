package com.example.newappmotiv.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface DaoTask {
    @Insert
    suspend fun insertDayTask(dayTask: DayTask)

    @Insert
    suspend fun insertListDayTasks(dayTask: List<DayTask>)

    @Query("DELETE FROM dayTasks")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM dayTasks")
    suspend fun getDayTasks(): List<DayTask>

    @Query("UPDATE dayTasks SET ready = :newReady WHERE id = :taskId")
    suspend fun updateTaskReadyById(taskId: Int?, newReady: Boolean)

    @Query("UPDATE dayTasks SET inProcess = :newValue WHERE id = :taskId")
    suspend fun updateTaskInProcess(taskId: Int?, newValue: Boolean)
}