package com.example.newappmotiv.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "store")
data class StoresItem (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val price: Float,
    var bought: Boolean,
    var timeValue: Int, // таймер в минутах
    var isAlreadyActive: Boolean // активированна ли покупка
)