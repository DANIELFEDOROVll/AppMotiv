package com.example.newappmotiv.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newappmotiv.databinding.ActivityAllStatsBinding
import com.example.newappmotiv.model.recyclerView.AdapterAllStats
import org.koin.androidx.viewmodel.ext.android.viewModel


class AllStatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllStatsBinding
    private val viewModel by viewModel<AllStatsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecycler()

        binding.tvSpentTimeAllTasks.text = "Проведенное время за задачами(часов): "
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadTasks()
    }

    private fun setRecycler(){
        val recyclerView = binding.rcAllStats
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = AdapterAllStats(emptyList())
        recyclerView.adapter = adapter

        viewModel.tasks.observe(this){
            adapter.updateTasks(it)
        }
    }
}