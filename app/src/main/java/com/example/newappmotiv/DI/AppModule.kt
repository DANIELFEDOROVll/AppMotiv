package com.example.newappmotiv.DI

import android.content.Context
import androidx.room.Room
import com.example.newappmotiv.model.room.AppDatabase
import com.example.newappmotiv.model.room.DaoGeneralTask
import com.example.newappmotiv.model.room.DaoTask
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app_database"
    ).build()

    @Provides
    fun provideDayTaskDao(database: AppDatabase): DaoTask = database.getDaoTasks()

    @Provides
    fun provideGeneralTaskDao(database: AppDatabase): DaoGeneralTask = database.getDaoGeneralTasks()
}