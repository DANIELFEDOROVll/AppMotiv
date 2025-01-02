package com.example.newappmotiv.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.newappmotiv.R
import com.example.newappmotiv.model.DayTasksRepository
import com.example.newappmotiv.model.GeneralTasksRepository
import com.example.newappmotiv.model.StoreRepository
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import com.example.newappmotiv.ui.profile.ProfileFragment
import com.example.newappmotiv.ui.store.StoreFragment
import com.example.newappmotiv.ui.store.StoreViewModel
import com.example.newappmotiv.ui.tasks.TasksFragment
import com.example.newappmotiv.ui.tasks.TasksViewModel
import com.example.newappmotiv.utils.MyApplication
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var viewModel: TasksViewModel
    private lateinit var storeViewModel: StoreViewModel
    private val database by lazy {
        (application as MyApplication).database
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.menu_tasks -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, TasksFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_shop -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, StoreFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_profile -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ProfileFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TasksFragment())
            .commit()

        val dayTasksRepository = DayTasksRepository(database.getDaoTasks())
        val generalTasksRepository = GeneralTasksRepository(database.getDaoGeneralTasks())
        val storeRepository = StoreRepository(database.getDaoStore())

        val preferencesManager = PreferencesManager(this)
        viewModel = ViewModelProvider(this,
            TasksViewModelFactory(
                dayTasksRepository,
                generalTasksRepository,
                preferencesManager
            )
        )[TasksViewModel::class.java]

        storeViewModel = ViewModelProvider(this,
            StoreViewModelFactory(
                storeRepository,
                preferencesManager
            ))[StoreViewModel::class.java]
    }

    fun getTasksViewModel() = viewModel
    fun getStoreViewModel() = storeViewModel

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

    class StoreViewModelFactory(
        private val storeRepository: StoreRepository,
        private val preferencesManager: PreferencesManager
    ) : ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StoreViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StoreViewModel(storeRepository, preferencesManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}