package com.example.newappmotiv.model.dagger

import android.content.Context
import com.example.newappmotiv.model.DayTasksRepository
import com.example.newappmotiv.model.GeneralTasksRepository
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import com.example.newappmotiv.utils.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: MyApplication) {

    @Provides
    @Singleton
    fun provideDayTasksRepository(): DayTasksRepository {
        return DayTasksRepository(application.database.getDaoTasks())
    }

    @Provides
    @Singleton
    fun provideGeneralTasksRepository(): GeneralTasksRepository {
        return GeneralTasksRepository(application.database.getDaoGeneralTasks())
    }

    @Provides
    @Singleton
    fun providePreferencesManager(context: Context): PreferencesManager {
        return PreferencesManager(context)
    }
}