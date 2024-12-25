package com.example.newappmotiv.ui.profile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newappmotiv.R
import com.example.newappmotiv.databinding.ActivityAllStatsBinding
import com.example.newappmotiv.model.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
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