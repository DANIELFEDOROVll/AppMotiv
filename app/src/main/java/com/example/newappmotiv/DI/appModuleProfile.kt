package com.example.newappmotiv.DI

import com.example.newappmotiv.ui.profile.AllStatsViewModel
import com.example.newappmotiv.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModuleProfile = module {
    viewModel{
        ProfileViewModel(get())
    }
    viewModel{
        AllStatsViewModel(get())
    }
}