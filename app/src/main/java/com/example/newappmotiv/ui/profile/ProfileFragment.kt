package com.example.newappmotiv.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.newappmotiv.R
import com.example.newappmotiv.databinding.FragmentProfileBinding
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint



class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var viewModel: ProfileViewModel

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

        viewModel = ViewModelProvider(this,
            ProfileViewModel.ProfileViewModelFactory(
                preferencesManager
            )
        )[ProfileViewModel::class.java]

        observeBalance()

        binding.button1.setOnClickListener{
            toAddGeneralTaskActivity()
        }

        binding.buttonAllStats.setOnClickListener {
            toAllStatsActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNowBalance()
    }

    private fun toAddGeneralTaskActivity(){
        val intent = Intent(requireContext(), AddGeneralTaskActivity::class.java)
        startActivity(intent)
    }

    private fun toAllStatsActivity(){
        val intent = Intent(requireContext(), AllStatsActivity::class.java)
        startActivity(intent)
    }

    private fun observeBalance(){
        viewModel.nowBalance.observe(viewLifecycleOwner){
            val text = "Балланс: $it"
            binding.balanceTextView.text = text
        }
    }
}
