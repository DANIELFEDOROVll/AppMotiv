package com.example.newappmotiv.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newappmotiv.databinding.ActivityAllStatsBinding
import com.example.newappmotiv.model.GeneralTasksRepository
import com.example.newappmotiv.model.recyclerView.AdapterAllStats
import com.example.newappmotiv.utils.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllStatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllStatsBinding
    private val database by lazy {
        (application as MyApplication).database
    }
    private lateinit var viewModel: AllStatsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = GeneralTasksRepository(database.getDaoGeneralTasks())
        viewModel = ViewModelProvider(this,
            AllStatsViewModel.AllStatsViewModelFactory(repository)
        )[AllStatsViewModel::class.java]

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