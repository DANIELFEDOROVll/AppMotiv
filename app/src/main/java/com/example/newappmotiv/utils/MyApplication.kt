package com.example.newappmotiv.utils

import android.app.Application
import com.example.newappmotiv.model.room.AppDatabase

class MyApplication: Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getDatabase(this)
    }
}