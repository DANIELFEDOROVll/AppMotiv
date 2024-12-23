package com.example.newappmotiv.model

import android.app.Application
import android.util.Log
import com.example.newappmotiv.model.room.AppDatabase

class MyApplication: Application() {
    lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        Log.i("hi", "hi")
        database = AppDatabase.getDatabase(this)


    }
}