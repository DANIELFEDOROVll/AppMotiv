package com.example.newappmotiv.model.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "dayTasks",
    foreignKeys = [ForeignKey(entity = GeneralTasks::class,
        parentColumns = ["name"],
        childColumns = ["generalTaskName"],
        onDelete = ForeignKey.CASCADE)]
)
data class DayTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val generalTaskName: String,
    val price: Float,
    var ready: Boolean,
    var timeValue: Int, // кол-во минут в задаче
    var inProcess: Boolean
)