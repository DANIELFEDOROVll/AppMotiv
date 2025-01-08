package com.example.newappmotiv.ui.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newappmotiv.model.room.DaoStore
import com.example.newappmotiv.model.room.StoresItem
import kotlinx.coroutines.launch

class AddItemViewModel(private val daoStore: DaoStore): ViewModel() {
    fun addItem(item: StoresItem){
        viewModelScope.launch {
            daoStore.insertItem(item)
        }
    }

    fun deleteAllItems(){
        viewModelScope.launch {
            daoStore.deleteAllItems()
        }
    }
}