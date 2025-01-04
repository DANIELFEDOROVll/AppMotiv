package com.example.newappmotiv.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

    class ProfileViewModelFactory(
        private val preferencesManager: PreferencesManager
    ) : ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel(preferencesManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}