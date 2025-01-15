package com.example.newappmotiv.model.repositories

import com.example.newappmotiv.model.room.DaoGeneralTask
import com.example.newappmotiv.model.room.GeneralTasks


class GeneralTasksRepository (private val daoGeneralTask: DaoGeneralTask) {
    suspend fun insGeneralTask(task: GeneralTasks) = daoGeneralTask.insertGeneralTask(task)
    suspend fun getGeneralTasks() = daoGeneralTask.getGeneralTasks()
    suspend fun getTotalSpentTimeOfTheTask(name: String) = daoGeneralTask.getTotalSpentTime(name)
    suspend fun updateTotalSpentTimeOfTheTask(name: String, newValue: Int)
    = daoGeneralTask.updateTotalSpentMinutes(name, newValue)
}