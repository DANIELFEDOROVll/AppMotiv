package com.example.newappmotiv.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import kotlinx.coroutines.launch


class ProfileViewModel(private val preferencesManager: PreferencesManager): ViewModel() {
    private val _nowBalance = MutableLiveData<Float>()
    val nowBalance: LiveData<Float> get() = _nowBalance

    init {
        getNowBalance()
    }

    fun getNowBalance(){
        viewModelScope.launch {
            _nowBalance.value = preferencesManager.getNowBalance()
        }
    }
}