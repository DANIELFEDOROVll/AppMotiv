package com.example.newappmotiv.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoStore {
    @Insert
    suspend fun insertItem(storesItem: StoresItem)

    @Query("SELECT * FROM store")
    suspend fun getAllItems(): List<StoresItem>

    @Query("UPDATE store SET bought = :newReady WHERE id = :taskId")
    suspend fun updateTaskReadyById(taskId: Int?, newReady: Boolean)

    @Query("DELETE FROM store")
    suspend fun deleteAllItems()

    @Query("UPDATE store SET isAlreadyActive = :newValue WHERE id = :itemId")
    suspend fun updateIsAlreadyActive(itemId: Int?, newValue: Boolean)
}