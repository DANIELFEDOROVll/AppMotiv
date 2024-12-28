package com.example.newappmotiv.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newappmotiv.databinding.ActivityAddGeneralTaskBinding
import com.example.newappmotiv.utils.MyApplication
import com.example.newappmotiv.model.room.AppDatabase
import com.example.newappmotiv.model.room.GeneralTasks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddGeneralTaskActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddGeneralTaskBinding
    lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGeneralTaskBinding.inflate(layoutInflater)

        setContentView(binding.root)
        database = (application as MyApplication).database

        binding.buttonAddGeneralTask.setOnClickListener {
            val task = GeneralTasks(
                binding.edName.text.toString(),
                binding.edDescription.text.toString(),
                0
            )
            CoroutineScope(Dispatchers.IO).launch {
                database.getDaoGeneralTasks().insertGeneralTask(task)
            }
        }
    }
}