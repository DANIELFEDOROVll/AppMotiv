package com.example.newappmotiv.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newappmotiv.model.GeneralTasksRepository
import com.example.newappmotiv.model.room.GeneralTasks
import kotlinx.coroutines.launch

class AllStatsViewModel(private val repository: GeneralTasksRepository): ViewModel() {
    private val _tasks = MutableLiveData<List<GeneralTasks>>()
    val tasks: LiveData<List<GeneralTasks>> get() = _tasks

    fun loadTasks(){
        viewModelScope.launch {
            _tasks.value = repository.getGeneralTasks()
        }
    }
}