package com.example.newappmotiv.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.newappmotiv.R
import com.example.newappmotiv.ui.profile.ProfileFragment
import com.example.newappmotiv.ui.store.StoreFragment
import com.example.newappmotiv.ui.tasks.TasksFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var tasksFragment: TasksFragment
    private lateinit var storeFragment: StoreFragment
    private lateinit var profileFragment: ProfileFragment

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
    }


    companion object{
        // ключи для sharedPreference
        const val SHARED_PREF_ONE_KEY = "shered_pref_one"
        const val BALANCE_NOW_KEY = "balance_now_key" //ключ текущего балланса
        const val BALANCE_ALLTIME_KEY = "ballance_all_time_key" //балланс за все время
        const val COSTS_ALLTIME_KEY = "costs_all_time_key" //потрачено за все время
    }

}