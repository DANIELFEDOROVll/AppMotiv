package com.example.newappmotiv.model.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newappmotiv.R
import com.example.newappmotiv.model.room.StoresItem
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import com.example.newappmotiv.ui.MainActivity


class MyAdapterForStore(private val tasksForRecycler: List<StoresItem>,
                private val onClickButtonBuy: (StoresItem) -> Boolean,
                private val onClickButtonAct: (StoresItem) -> Unit,
                private val onClickButtonCancel: (StoresItem) -> Unit) : RecyclerView.Adapter<MyAdapterForStore.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_for_store, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasksForRecycler[position])
    }

    override fun getItemCount(): Int {
        return tasksForRecycler.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonBuy = itemView.findViewById<Button>(R.id.buttonBuy)
        val buttonAct = itemView.findViewById<Button>(R.id.buttonAct)
        val buttonCancel = itemView.findViewById<Button>(R.id.buttonCancel)
        val name: TextView = itemView.findViewById(R.id.textViewName)

        fun bind(item: StoresItem) {
            name.text = item.name
            if(item.bought){
                buttonAct.visibility = View.VISIBLE
                buttonCancel.visibility = View.VISIBLE
                buttonBuy.visibility = View.GONE
            }
            if(item.isAlreadyActive){
                buttonAct.visibility = View.GONE
                buttonCancel.visibility = View.GONE
                buttonBuy.visibility = View.GONE
                name.text = name.text.toString() + ": Активированно"
            }

            buttonAct.setOnClickListener {
                buttonCancel.visibility = View.GONE
                buttonAct.visibility = View.GONE
                name.text = name.text.toString() + ": Активированно"
                onClickButtonAct(item)
            }

            buttonBuy.setOnClickListener {
                item.bought = true
                val d = onClickButtonBuy(item)

                if(d) {
                    buttonBuy.visibility = View.GONE
                    buttonAct.visibility = View.VISIBLE
                    buttonCancel.visibility = View.VISIBLE
                }
                else item.bought = false
            }

            buttonCancel.setOnClickListener {
                item.bought = false

                buttonCancel.visibility = View.GONE
                buttonAct.visibility = View.GONE
                buttonBuy.visibility = View.VISIBLE

                onClickButtonCancel(item)
            }
        }
    }
}