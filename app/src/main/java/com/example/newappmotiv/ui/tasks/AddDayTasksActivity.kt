package com.example.newappmotiv.ui.tasks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newappmotiv.databinding.ActivityAddDayTasksBinding
import com.example.newappmotiv.model.DayTasksRepository
import com.example.newappmotiv.model.GeneralTasksRepository
import com.example.newappmotiv.utils.MyApplication
import com.example.newappmotiv.model.recyclerView.AdapterForAddDayTasks
import com.example.newappmotiv.model.recyclerView.One
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import com.example.newappmotiv.ui.tasks.TasksFragment.TasksViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddDayTasksActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddDayTasksBinding
    private val database by lazy {
        (application as MyApplication).database
    }

    private lateinit var viewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDayTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dayTasksRepository = DayTasksRepository(database.getDaoTasks())
        val generalTasksRepository = GeneralTasksRepository(database.getDaoGeneralTasks())
        val preferencesManager = PreferencesManager(this)

        val viewModel = ViewModelProvider(this,
            TasksViewModelFactory(
                dayTasksRepository, generalTasksRepository,
                preferencesManager
            )
        )[TasksViewModel::class.java]

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
                viewModel.loadTasks()
            }
            finish()
        }

        binding.buttonDeleteAllTasks.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                database.getDaoTasks().deleteAllTasks()
                viewModel.loadTasks()
            }
            finish()
        }
    }

    class TasksViewModelFactory(
        private val repository: DayTasksRepository,
        private val repositoryGeneralTask: GeneralTasksRepository,
        private val preferencesManager: PreferencesManager
    ) : ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TasksViewModel(repository, repositoryGeneralTask, preferencesManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}