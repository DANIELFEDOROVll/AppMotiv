package com.example.newappmotiv.ui.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newappmotiv.model.DayTasksRepository
import com.example.newappmotiv.model.GeneralTasksRepository
import com.example.newappmotiv.model.room.AppDatabase
import com.example.newappmotiv.model.room.DaoStore
import com.example.newappmotiv.model.room.StoresItem
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import com.example.newappmotiv.ui.tasks.TasksViewModel
import kotlinx.coroutines.launch

class AddItemViewModel(private val daoStore: DaoStore): ViewModel() {
    fun addItem(item: StoresItem){
        viewModelScope.launch {
            daoStore.insertItem(item)
        }
    }

    fun deleteAllItems(){
        viewModelScope.launch {
            daoStore.deleteAllItems()
        }
    }

    class AddItemViewModelFactory(
        private val daoStore: DaoStore
    ) : ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddItemViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddItemViewModel(daoStore) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}