package com.example.newappmotiv.DI

import com.example.newappmotiv.model.DayTasksRepository
import com.example.newappmotiv.model.GeneralTasksRepository
import com.example.newappmotiv.model.room.AppDatabase
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import com.example.newappmotiv.ui.tasks.AddDayTaskViewModel
import com.example.newappmotiv.ui.tasks.TasksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModuleTasksViewModel = module {
    single { AppDatabase.getDatabase(androidContext()) }
    single { get<AppDatabase>().getDaoTasks() }
    single { get<AppDatabase>().getDaoGeneralTasks()}
    single { DayTasksRepository(get()) }
    single { GeneralTasksRepository(get())}
    single { PreferencesManager(androidContext()) }
    viewModel { TasksViewModel(
        repositoryDayTask = get(),
        repositoryGeneralTask = get(),
        preferencesManager = get()
    ) }
    viewModel {
        AddDayTaskViewModel(get(), get())
    }
}