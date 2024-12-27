package com.example.newappmotiv.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newappmotiv.R
import com.example.newappmotiv.databinding.FragmentProfileBinding
import com.example.newappmotiv.model.sharedPreference.PreferencesManager


class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding

    lateinit var preferencesManager: PreferencesManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesManager = PreferencesManager(requireContext())

        binding.balanceTextView.text = "Текущий балланс: ${preferencesManager.getNowBalance()}"

        binding.button1.setOnClickListener{
            toAddGeneralTaskActivity()
        }

        binding.buttonAllStats.setOnClickListener {
            toAllStatsActivity()
        }
    }

    private fun toAddGeneralTaskActivity(){
        val intent = Intent(requireContext(), AddGeneralTaskActivity::class.java)
        startActivity(intent)
    }

    private fun toAllStatsActivity(){
        val intent = Intent(requireContext(), AllStatsActivity::class.java)
        startActivity(intent)
    }
}
