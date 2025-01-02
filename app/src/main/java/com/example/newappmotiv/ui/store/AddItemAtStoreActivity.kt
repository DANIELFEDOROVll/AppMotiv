package com.example.newappmotiv.ui.store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newappmotiv.databinding.ActivityAddItemAtStoreBinding
import com.example.newappmotiv.utils.MyApplication
import com.example.newappmotiv.model.room.StoresItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddItemAtStoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemAtStoreBinding
    private val database by lazy {
        (this.application as MyApplication).database
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemAtStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val item = StoresItem(
                null,
                binding.edName.text.toString(),
                binding.edPrice.text.toString().toFloat(),
                false,
                binding.edTimeValue.text.toString().toInt(),
                false
            )
            CoroutineScope(Dispatchers.IO).launch {
                database.getDaoStore().insertItem(item)
            }
            finish()
        }

        binding.button4.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                database.getDaoStore().deleteAllItems()
            }
            finish()
        }

    }
}