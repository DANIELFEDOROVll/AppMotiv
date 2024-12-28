package com.example.newappmotiv.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "generalTasks")
data class GeneralTasks(
    @PrimaryKey
    val name: String,
    val description: String,
    var totalSpentTimeInMinutes: Int
)