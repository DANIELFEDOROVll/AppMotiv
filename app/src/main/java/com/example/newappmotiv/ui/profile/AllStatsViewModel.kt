package com.example.newappmotiv.ui.profile

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newappmotiv.model.GeneralTasksRepository
import com.example.newappmotiv.model.room.GeneralTasks
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import kotlinx.coroutines.launch

class AllStatsViewModel(private val repository: GeneralTasksRepository): ViewModel() {
    private val _tasks = MutableLiveData<List<GeneralTasks>>()
    val tasks: LiveData<List<GeneralTasks>> get() = _tasks

    fun loadTasks(){
        viewModelScope.launch {
            _tasks.value = repository.getGeneralTasks()
        }
    }

    class AllStatsViewModelFactory(
        private val repository: GeneralTasksRepository
    ) : ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AllStatsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AllStatsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}