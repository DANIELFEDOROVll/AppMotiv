package com.example.newappmotiv.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newappmotiv.model.dayTasksRepository
import com.example.newappmotiv.model.room.AppDatabase
import com.example.newappmotiv.model.room.DayTask
import kotlinx.coroutines.launch

class TasksViewModel(private val repository: dayTasksRepository): ViewModel() {
    private val _tasks = MutableLiveData<List<DayTask>>()
    val tasks: LiveData<List<DayTask>>
        get() = _tasks

    private fun getDayTasks(){
        viewModelScope.launch {
            repository.getDayTasks()
        }
    }
}