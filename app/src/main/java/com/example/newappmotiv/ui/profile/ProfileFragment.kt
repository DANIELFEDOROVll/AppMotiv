package com.example.newappmotiv.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newappmotiv.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModel<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
