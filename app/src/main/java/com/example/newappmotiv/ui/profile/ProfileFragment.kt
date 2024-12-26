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

        binding.totalBalanceTextView.text = "Заработанно за все время:  ${preferencesManager.getAllTimeBalance()}"

        binding.balanceTextView.text = "Текущий балланс: ${preferencesManager.getNowBalance()}"

        binding.totalSpentTextView.text = "Потраченно за все время: ${preferencesManager.getSpentAllTime()}"

        binding.button1.setOnClickListener{
            toAddGeneralTaskActivity()
        }

        binding.buttonAllStats.setOnClickListener {

        }
    }

    fun toAddGeneralTaskActivity(){
        val intent = Intent(requireContext(), AddGeneralTaskActivity::class.java)
        startActivity(intent)
    }
}
