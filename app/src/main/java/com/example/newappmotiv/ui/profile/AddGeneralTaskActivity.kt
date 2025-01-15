package com.example.newappmotiv.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newappmotiv.databinding.ActivityAddGeneralTaskBinding
import com.example.newappmotiv.model.room.GeneralTasks
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddGeneralTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddGeneralTaskBinding
    private val viewModel by viewModel<AddGeneralTaskViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGeneralTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAddGeneralTask.setOnClickListener {
            clickAdd()
        }
    }

    private fun clickAdd(){
        val task = GeneralTasks(
            binding.edName.text.toString(),
            binding.edDescription.text.toString(),
            0
        )
        viewModel.insTask(task)
        finish()
    }
}