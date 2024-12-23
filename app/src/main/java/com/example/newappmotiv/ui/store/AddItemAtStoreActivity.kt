package com.example.newappmotiv.ui.store

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newappmotiv.R
import com.example.newappmotiv.databinding.ActivityAddItemAtStoreBinding
import com.example.newappmotiv.model.MyApplication
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
        }

        binding.button4.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                database.getDaoStore().deleteAllItems()
            }
        }

    }
}