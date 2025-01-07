package com.example.newappmotiv.ui.tasks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newappmotiv.databinding.ActivityAddDayTasksBinding
import com.example.newappmotiv.model.recyclerView.AdapterForAddDayTasks
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddDayTasksActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddDayTasksBinding
    private val viewModel by viewModel<AddDayTaskViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDayTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.buttonAddDaytask.setOnClickListener {
            viewModel.addDayTasks()
            finish()
        }

        binding.buttonDeleteAllTasks.setOnClickListener {
            viewModel.deleteDayTasks()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadTasks()
    }

    private fun setupRecyclerView(){
        val recyclerView = binding.recViewAddDaytask
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = AdapterForAddDayTasks(emptyList())
        recyclerView.adapter = adapter

        viewModel.tasks.observe(this){
            adapter.updateTasks(it)
        }
    }
}