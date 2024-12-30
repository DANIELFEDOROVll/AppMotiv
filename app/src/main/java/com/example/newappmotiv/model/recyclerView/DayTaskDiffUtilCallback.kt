package com.example.newappmotiv.model.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.example.newappmotiv.model.room.DayTask

class DayTaskDiffUtilCallback : DiffUtil.ItemCallback<DayTask>() {
    override fun areItemsTheSame(oldItem: DayTask, newItem: DayTask): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DayTask, newItem: DayTask): Boolean {
        return oldItem == newItem
    }
}