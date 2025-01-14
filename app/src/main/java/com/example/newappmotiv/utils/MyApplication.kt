package com.example.newappmotiv.utils

import android.app.Application
import com.example.newappmotiv.DI.appModuleProfile
import com.example.newappmotiv.DI.appModuleStore
import com.example.newappmotiv.DI.appModuleTasks
import com.example.newappmotiv.model.room.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MyApplication: Application() {
    lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getDatabase(this)
        startKoin {
            androidContext(this@MyApplication)
            modules(
                appModuleTasks,
                appModuleStore,
                appModuleProfile
            )
        }
    }
}