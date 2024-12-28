package com.example.newappmotiv.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newappmotiv.model.room.DayTask
import kotlinx.coroutines.launch

class TasksViewModel: ViewModel() {
    val _tasks = MutableLiveData<List<DayTask>>()
    val tasks = LiveData<List<DayTask>>
        get() = _tasks

    private fun getDayTasks(){
        viewModelScope.launch {
//---
        }
    }
}