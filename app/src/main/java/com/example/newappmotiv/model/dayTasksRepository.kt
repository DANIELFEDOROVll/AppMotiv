package com.example.newappmotiv.model

import androidx.lifecycle.LiveData
import com.example.newappmotiv.model.room.DaoTask
import com.example.newappmotiv.model.room.DayTask

class dayTasksRepository(private val daoTask: DaoTask) {
    val dayTasks: LiveData<List<DayTask>> = daoTask.getDayTasks()
    //---
}