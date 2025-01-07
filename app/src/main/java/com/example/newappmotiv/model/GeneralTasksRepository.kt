package com.example.newappmotiv.model

import com.example.newappmotiv.model.room.DaoGeneralTask


class GeneralTasksRepository (private val daoGeneralTask: DaoGeneralTask) {
    suspend fun getGeneralTasks() = daoGeneralTask.getGeneralTasks()
    suspend fun getTotalSpentTimeOfTheTask(name: String) = daoGeneralTask.getTotalSpentTime(name)
    suspend fun updateTotalSpentTimeOfTheTask(name: String, newValue: Int)
    = daoGeneralTask.updateTotalSpentMinutes(name, newValue)
}