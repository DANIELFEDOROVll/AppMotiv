package com.example.newappmotiv.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newappmotiv.model.repositories.GeneralTasksRepository
import com.example.newappmotiv.model.room.GeneralTasks
import kotlinx.coroutines.launch

class AddGeneralTaskViewModel(
    private val repositoryGeneralTask: GeneralTasksRepository
): ViewModel() {
    fun insTask(task: GeneralTasks){
        viewModelScope.launch{
            repositoryGeneralTask.insGeneralTask(task)
        }
    }
}