package com.example.newappmotiv.DI

import com.example.newappmotiv.model.repositories.StoreRepository
import com.example.newappmotiv.model.room.AppDatabase
import com.example.newappmotiv.ui.store.AddItemViewModel
import com.example.newappmotiv.ui.store.StoreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModuleStore = module {
    single { get<AppDatabase>().getDaoStore() }
    single { StoreRepository(get()) }
    viewModel{
        StoreViewModel(
            storeRepository = get(),
            preferencesManager = get()
        )
    }
    viewModel {
        AddItemViewModel(get())
    }
}