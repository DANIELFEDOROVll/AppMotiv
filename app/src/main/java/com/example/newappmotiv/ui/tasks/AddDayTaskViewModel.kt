package com.example.newappmotiv.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newappmotiv.model.DayTasksRepository
import com.example.newappmotiv.model.GeneralTasksRepository
import com.example.newappmotiv.model.recyclerView.One
import com.example.newappmotiv.model.room.GeneralTasks
import kotlinx.coroutines.launch

class AddDayTaskViewModel(
    private val repositoryDayTask: DayTasksRepository,
    private val repositoryGeneralTask: GeneralTasksRepository
): ViewModel() {

    private val _tasks = MutableLiveData<List<GeneralTasks>>()
    val tasks: LiveData<List<GeneralTasks>> get() = _tasks

    fun loadTasks(){
        viewModelScope.launch {
            _tasks.value = repositoryGeneralTask.getGeneralTasks()
        }
    }

    fun addDayTasks(){
        viewModelScope.launch {
            repositoryDayTask.insertListDayTasks(One.listOfTasks.toList())
        }
    }

    fun deleteDayTasks(){
        viewModelScope.launch {
            repositoryDayTask.deleteAll()
        }
    }
}