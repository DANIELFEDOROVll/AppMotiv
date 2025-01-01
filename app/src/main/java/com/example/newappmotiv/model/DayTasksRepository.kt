package com.example.newappmotiv.model

import com.example.newappmotiv.model.room.DaoTask
import com.example.newappmotiv.model.room.DayTask

class DayTasksRepository(private val daoTask: DaoTask) {
    suspend fun getDayTasks() = daoTask.getDayTasks()
    suspend fun insertDayTask(dayTask: DayTask) = daoTask.insertDayTask(dayTask)
    suspend fun updateTaskInProcess(id: Int?, newValue: Boolean) = daoTask.updateTaskInProcess(id, newValue)
    suspend fun updateTaskReady(id: Int?, newValue: Boolean) = daoTask.updateTaskReadyById(id, newValue)

}