package com.example.newappmotiv.ui.store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.newappmotiv.databinding.ActivityAddItemAtStoreBinding
import com.example.newappmotiv.utils.MyApplication
import com.example.newappmotiv.model.room.StoresItem


class AddItemAtStoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemAtStoreBinding
    private val database by lazy {
        (this.application as MyApplication).database
    }
    private lateinit var viewModel: AddItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemAtStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
            AddItemViewModel.Factory(database.getDaoStore())
        )[AddItemViewModel::class.java]

        binding.button.setOnClickListener {
            addItem()
        }

        binding.button4.setOnClickListener {
            deleteAllItems()
        }
    }

    private fun addItem(){
        val item = StoresItem(
            null,
            binding.edName.text.toString(),
            binding.edPrice.text.toString().toFloat(),
            false,
            binding.edTimeValue.text.toString().toInt(),
            false
        )
        viewModel.addItem(item)
        finish()
    }

    private fun deleteAllItems(){
        viewModel.deleteAllItems()
        finish()
    }
}