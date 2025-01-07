package com.example.newappmotiv.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newappmotiv.model.GeneralTasksRepository
import com.example.newappmotiv.model.DayTasksRepository
import com.example.newappmotiv.model.room.DayTask
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import kotlinx.coroutines.launch
import kotlin.random.Random


class TasksViewModel(
    private val repositoryDayTask: DayTasksRepository,
    private val repositoryGeneralTask: GeneralTasksRepository,
    private val preferencesManager: PreferencesManager
): ViewModel() {

    private val _tasks = MutableLiveData<List<DayTask>>()
    val tasks: LiveData<List<DayTask>> get() = _tasks

    private val _toast_message = SingleLiveEvent<String>()
    val toast_message: LiveData<String> get() = _toast_message

    private val _secondTimer = SingleLiveEvent<Int>()
    val secondTimer: LiveData<Int> get() = _secondTimer

    fun loadTasks(){
        viewModelScope.launch {
            val newTasks = repositoryDayTask.getDayTasks()
            _tasks.postValue(newTasks)
        }
    }

    fun clickStart(t: DayTask){
        viewModelScope.launch {
            repositoryDayTask.updateTaskInProcess(t.id, true)
        }
        val durationInMinutes = t.timeValue
        _secondTimer.value = durationInMinutes * 60
    }

    fun installBalance(t: DayTask){// меняет в сущности ready и считает баллы за задание
        viewModelScope.launch {
            repositoryDayTask.updateTaskReady(t.id, t.ready) //меняем ready в сущности
            if(t.ready){ // при выполнении задания
                preferencesManager.updateNowBalanceForReadyTasks(t.price)
                _toast_message.postValue(getMessageForToast(t.price))
            }
            if(!t.ready){ // при отмене задания
                preferencesManager.updateNowBalanceForCancelTasks(t.price)
            }
            addMinutesInTotalSpentTime(t.generalTaskName, t.timeValue, t.ready)
            loadTasks()
        }
    }

    private fun addMinutesInTotalSpentTime(name: String, minutes: Int, ready: Boolean){
        if(ready) {
            viewModelScope.launch {
                val spentTime = repositoryGeneralTask.getTotalSpentTimeOfTheTask(name)
                repositoryGeneralTask.updateTotalSpentTimeOfTheTask(name, spentTime + minutes)
            }
        }
        else{
            viewModelScope.launch {
                val spentTime = repositoryGeneralTask.getTotalSpentTimeOfTheTask(name)
                repositoryGeneralTask.updateTotalSpentTimeOfTheTask(name, spentTime - minutes)
            }
        }
    }

    private fun getMessageForToast(num: Float): String{
        val messages = listOf(
            "Молодец! Вот твои баллы!",
            "Хорош, держи свои заработаные баллы",
            "Неплохо, давай в таком же темпе.",
            "Потрудился - получил! Держи бро!"
        )
        return messages[Random.nextInt(1,4)] + " + ${num} баллов!"
    }
}