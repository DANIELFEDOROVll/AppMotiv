package com.example.newappmotiv.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newappmotiv.R
import com.example.newappmotiv.ui.profile.ProfileFragment
import com.example.newappmotiv.ui.store.StoreFragment
import com.example.newappmotiv.ui.tasks.TasksFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Установка слушателя для выбора элементов
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_tasks -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, TasksFragment()).commit()
                    true
                }
                R.id.menu_shop -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, StoreFragment()).commit()
                    true
                }
                R.id.menu_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment()).commit()
                    true
                }
                else -> false
            }
        }
        // Инициализация TasksFragment по умолчанию
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TasksFragment())
            .commit()

    }
}