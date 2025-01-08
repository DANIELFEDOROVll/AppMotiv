package com.example.newappmotiv.model.repositories

import com.example.newappmotiv.model.room.DaoStore
import com.example.newappmotiv.model.room.StoresItem

class StoreRepository(private val daoStore: DaoStore) {
    suspend fun insertItem(item: StoresItem) = daoStore.insertItem(item)
    suspend fun getAll() = daoStore.getAllItems()
    suspend fun updateTaskReadyById(id: Int?, ready: Boolean) = daoStore.updateTaskReadyById(id, ready)
    suspend fun deleteAll() = daoStore.deleteAllItems()
    suspend fun updateIsAlreadyActive(id: Int?, value: Boolean) = daoStore.updateIsAlreadyActive(id, value)
}