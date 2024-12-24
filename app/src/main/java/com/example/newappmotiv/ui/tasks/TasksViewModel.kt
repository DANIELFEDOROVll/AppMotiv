package com.example.newappmotiv.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newappmotiv.model.room.AppDatabase
import com.example.newappmotiv.model.room.DayTask
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasksViewModel(
    private val database: AppDatabase,
    private val preferencesManager: PreferencesManager
): ViewModel() {

    private val _tasks = MutableLiveData<List<DayTask>>()
    val tasks: LiveData<List<DayTask>> get() = _tasks

    fun loadTasks() {
        viewModelScope.launch {
            val ts = database.getDaoTasks().getDayTasks()
            _tasks.postValue(ts)
        }
    }

    fun updateTaskInProcess(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            database.getDaoTasks().updateTaskInProcess(taskId, true)
        }
    }

    fun installBalance(task: DayTask) {
        viewModelScope.launch(Dispatchers.IO) {
            database.getDaoTasks().updateTaskReadyById(task.id, task.ready)
            if (task.ready) {
                preferencesManager.updateNowBalanceForTasks(preferencesManager.getNowBalance() + task.price)
            } else {
                preferencesManager.updateNowBalanceForTasks(preferencesManager.getNowBalance() - task.price)
            }
        }
    }
}

class TasksViewModelFactory(
    private val database: AppDatabase,
    private val preferencesManager: PreferencesManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            return TasksViewModel(database, preferencesManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}