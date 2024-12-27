package com.example.newappmotiv.model.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newappmotiv.R
import com.example.newappmotiv.model.room.GeneralTasks


class AdapterAllStats(private val generalTasks: List<GeneralTasks>) : RecyclerView.Adapter<AdapterAllStats.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_all_stats, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(generalTasks[position])
    }

    override fun getItemCount(): Int {
        return generalTasks.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTask = itemView.findViewById<TextView>(R.id.NameGeneralTask)
        val hoursTask = itemView.findViewById<TextView>(R.id.HoursGeneralTask)

        fun bind(generalTask: GeneralTasks) {
            val spent = "Проведено времени: " + (generalTask.totalSpentTimeInMinutes / 60).toString() +
                    " часов"

            val nm = "Название: ${generalTask.name}"

            nameTask.text = nm
            hoursTask.text = spent
        }
    }
}