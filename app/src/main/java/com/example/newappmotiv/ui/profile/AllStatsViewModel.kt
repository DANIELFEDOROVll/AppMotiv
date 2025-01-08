package com.example.newappmotiv.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newappmotiv.model.repositories.GeneralTasksRepository
import com.example.newappmotiv.model.room.GeneralTasks
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import kotlinx.coroutines.launch

class AllStatsViewModel(private val repository: GeneralTasksRepository,
    private val preferencesManager: PreferencesManager): ViewModel() {
    private val _tasks = MutableLiveData<List<GeneralTasks>>()
    val tasks: LiveData<List<GeneralTasks>> get() = _tasks

    private val _spentAllTime = MutableLiveData<Float>()
    val spentAllTime: LiveData<Float> get() = _spentAllTime

    fun loadTasks(){
        viewModelScope.launch {
            _tasks.value = repository.getGeneralTasks()
        }
    }

    fun getSpentAllTime(){
        _spentAllTime.value = preferencesManager.getSpentAllTime()
    }
}