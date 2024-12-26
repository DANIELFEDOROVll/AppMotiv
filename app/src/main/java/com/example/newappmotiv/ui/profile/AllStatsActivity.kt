package com.example.newappmotiv.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newappmotiv.databinding.ActivityAllStatsBinding
import com.example.newappmotiv.utils.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllStatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllStatsBinding

    private val database by lazy {
        (application as MyApplication).database
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        CoroutineScope(Dispatchers.IO).launch {

        }
    }
}