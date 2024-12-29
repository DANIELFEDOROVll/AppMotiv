package com.example.newappmotiv.ui.tasks

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newappmotiv.model.dayTasksRepository
import com.example.newappmotiv.model.room.DayTask
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class TasksViewModel(
    private val repositoryDayTask: dayTasksRepository,
    private val preferencesManager: PreferencesManager
): ViewModel() {

    private val _tasks = MutableLiveData<List<DayTask>>()
    val tasks: LiveData<List<DayTask>>
        get() = _tasks

    private val _toast_number = MutableLiveData<Float>()
    val toast_numer: LiveData<Float>
        get() = _toast_number

    init {
        loadTasks()
    }

    private fun loadTasks(){
        viewModelScope.launch {
            repositoryDayTask.getDayTasks()
        }
    }

    fun installBalance(t: DayTask){// меняет в сущности ready и считает баллы за задание
        viewModelScope.launch {
            repositoryDayTask.updateTaskReady(t.id, t.ready) //меняем ready в сущности
            if(t.ready){ // при выполнении задания
                preferencesManager.updateNowBalanceForReadyTasks(t.price)
                withContext(Dispatchers.Main){
                    _toast_number.value = t.price
                }
            }
            if(!t.ready){ // при отмене задания
                preferencesManager.updateNowBalanceForCancelTasks(t.price)
            }
            addMinutesInTotalSpentTime(t.generalTaskName, t.timeValue, t.ready)
        }
    }

    fun addMinutesInTotalSpentTime(name: String, minutes: Int, ready: Boolean){
        if(ready) {
            viewModelScope.launch {
                val spentTime = database.getDaoGeneralTasks().getTotalSpentTime(name)
                database.getDaoGeneralTasks().updateTotalSpentMinutes(name, spentTime + minutes)
            }
        }
        else{
            viewModelScope.launch {
                val spentTime = database.getDaoGeneralTasks().getTotalSpentTime(name)
                database.getDaoGeneralTasks().updateTotalSpentMinutes(name, spentTime - minutes)
            }
        }
    }
}