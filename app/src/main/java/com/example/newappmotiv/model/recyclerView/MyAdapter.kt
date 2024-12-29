package com.example.newappmotiv.model.recyclerView

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newappmotiv.R
import com.example.newappmotiv.model.room.DayTask


class MyAdapter(private var tasksForRecycler: List<DayTask>,
                private val timerStart: (DayTask) -> Unit,
                private val onTaskCheckedChangeListener: (DayTask) -> Unit) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasksForRecycler[position])
    }

    override fun getItemCount(): Int {
        return tasksForRecycler.size
    }

    fun updateTasks(tasks: List<DayTask>){
        tasksForRecycler = tasks
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textInProcess = itemView.findViewById<TextView>(R.id.textViewInProcess)
        val name: CheckBox = itemView.findViewById(R.id.checkBox)
        val price: TextView = itemView.findViewById(R.id.textPrice)
        val timeValue = itemView.findViewById<TextView>(R.id.textTimeValue)
        val startButton: Button = itemView.findViewById(R.id.buttonStartTask)


        fun bind(task: DayTask) {

            name.text = task.generalTaskName
            price.text = task.price.toString()
            name.isChecked = task.ready
            timeValue.text = task.timeValue.toString() + " мин."

            if(task.inProcess){
                textInProcess.visibility = View.VISIBLE
                startButton.visibility = View.GONE
            }

            name.setOnCheckedChangeListener{ _, isChecked ->
                task.ready = isChecked
                onTaskCheckedChangeListener(task)
            }

            startButton.setOnClickListener {
                textInProcess.visibility = View.VISIBLE
                startButton.visibility = View.GONE
                timerStart(task)
            }
        }
    }
}