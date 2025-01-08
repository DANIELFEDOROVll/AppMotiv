package com.example.newappmotiv.ui.store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newappmotiv.databinding.ActivityAddItemAtStoreBinding
import com.example.newappmotiv.model.room.StoresItem
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddItemAtStoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemAtStoreBinding
    private val viewModel by viewModel<AddItemViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemAtStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

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