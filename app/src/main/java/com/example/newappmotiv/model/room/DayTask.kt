package com.example.newappmotiv.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dayTasks")
data class DayTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val price: Float,
    var ready: Boolean,
    var timeValue: Int, // кол-во минут в задаче
    var inProcess: Boolean
)