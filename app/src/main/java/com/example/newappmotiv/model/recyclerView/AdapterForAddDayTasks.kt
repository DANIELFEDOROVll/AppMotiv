package com.example.newappmotiv.model.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newappmotiv.R
import com.example.newappmotiv.model.room.DayTask
import com.example.newappmotiv.model.room.GeneralTasks


class AdapterForAddDayTasks(private val generalTasks: List<GeneralTasks>): RecyclerView.Adapter<AdapterForAddDayTasks.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_general_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.startValues(generalTasks[position])
        holder.bind()
    }

    override fun getItemCount(): Int {
        return generalTasks.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkbox = itemView.findViewById<CheckBox>(R.id.checkBoxOne)
        val textDescription = itemView.findViewById<TextView>(R.id.textViewDescription)
        val edPrice = itemView.findViewById<EditText>(R.id.edPrice)
        val edTime = itemView.findViewById<EditText>(R.id.editTextTimeValue)
        val edCount = itemView.findViewById<EditText>(R.id.editTextCount)

        fun bind() {
            checkbox.setOnCheckedChangeListener{_, isChecked ->
                // доделать логику с выбором и добавлением задач
                val price = edPrice.text.toString()
                val time = edTime.text.toString()
                val count = edCount.text.toString()

                if(price != "" && time != "" && count != "") {
                    for (i in 0 until count.toInt()) {
                        One.listOfTasks.add(
                            DayTask(
                                null,
                                checkbox.text.toString(),
                                price.toFloat(),
                                false,
                                time.toInt(),
                                false
                            )
                        )
                    }
                }
            }
        }

        fun startValues(task: GeneralTasks){
            checkbox.text = task.name
            textDescription.text = task.description
        }
    }
}