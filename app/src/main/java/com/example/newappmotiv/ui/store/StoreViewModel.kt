package com.example.newappmotiv.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newappmotiv.model.StoreRepository
import com.example.newappmotiv.model.room.StoresItem
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import kotlinx.coroutines.launch

class StoreViewModel(
    private val storeRepository: StoreRepository,
    private val preferencesManager: PreferencesManager
    ): ViewModel() {
    private val _items = MutableLiveData<List<StoresItem>>()
    val items: LiveData<List<StoresItem>> get() = _items

    fun loadItems(){
        viewModelScope.launch {
            _items.value = storeRepository.getAll()
        }
    }
}