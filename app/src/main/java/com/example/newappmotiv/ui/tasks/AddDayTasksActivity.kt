package com.example.newappmotiv.ui.tasks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newappmotiv.databinding.ActivityAddDayTasksBinding
import com.example.newappmotiv.utils.MyApplication
import com.example.newappmotiv.model.recyclerView.AdapterForAddDayTasks
import com.example.newappmotiv.model.recyclerView.One
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddDayTasksActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddDayTasksBinding
    private val database by lazy {
        (application as MyApplication).database
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDayTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recViewAddDaytask
        recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.IO).launch {
            val tasks = database.getDaoGeneralTasks().getGeneralTasks()

            withContext(Dispatchers.Main) {
                val adapter = AdapterForAddDayTasks(tasks)
                recyclerView.adapter = adapter
            }
        }

        binding.buttonAddDaytask.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
            database.getDaoTasks().insertListDayTasks(One.listOfTasks.toList())
        }

        binding.buttonDeleteAllTasks.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                database.getDaoTasks().deleteAllTasks()
            }
        }
    }
}
}