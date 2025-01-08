package com.example.newappmotiv.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newappmotiv.model.repositories.StoreRepository
import com.example.newappmotiv.model.room.StoresItem
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import com.example.newappmotiv.ui.tasks.SingleLiveEvent
import kotlinx.coroutines.launch


class StoreViewModel(
    private val storeRepository: StoreRepository,
    private val preferencesManager: PreferencesManager
    ): ViewModel() {

    private val _items = MutableLiveData<List<StoresItem>>()
    val items: LiveData<List<StoresItem>> get() = _items

    private val _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private val _secondTimer = SingleLiveEvent<Int>()
    val secondTimer: LiveData<Int> get() = _secondTimer


    fun loadItems(){
        viewModelScope.launch {
            _items.value = storeRepository.getAll()
        }
    }

    fun boughtItem(item: StoresItem): Boolean{
        val work = preferencesManager.updateNowBalanceForBuy(item.price)
        if(!work) {
            _toastMessage.value = "Не хватает средств!"
            return false
        }
        else{
            viewModelScope.launch {
                storeRepository.updateTaskReadyById(item.id, item.bought)
            }
            _toastMessage.value = getTextBuyOrCancelItem(item.price, true)
            preferencesManager.updateSpentAllTime(item.price)
            return true
        }
    }

    private fun getTextBuyOrCancelItem(num: Float, buy: Boolean): String{
        val text: String = if(buy) "Потраченно на покупку ${num} баллов"
        else "Покупка отменена, баллы возвращенны"
        return text
    }

    fun cancelItem(item: StoresItem){
        viewModelScope.launch {
            storeRepository.updateTaskReadyById(item.id, item.bought)
        }
        preferencesManager.updateNowBalanceForCancel(item.price)
        _toastMessage.value = getTextBuyOrCancelItem(item.price, false)
    }

    fun actionTimerForStoreItem(item: StoresItem){
        val durationInMinutes = item.timeValue
        _secondTimer.value = durationInMinutes * 60
    }

    fun requestForChangeActivate(itemId: Int?){
        viewModelScope.launch {
            storeRepository.updateIsAlreadyActive(itemId, true)
        }
    }
}