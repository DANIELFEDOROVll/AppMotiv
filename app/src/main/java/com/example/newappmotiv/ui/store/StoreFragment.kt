package com.example.newappmotiv.ui.store

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newappmotiv.databinding.FragmentStoreBinding
import com.example.newappmotiv.model.StoreRepository
import com.example.newappmotiv.utils.MyApplication
import com.example.newappmotiv.model.recyclerView.MyAdapterForStore
import com.example.newappmotiv.model.sharedPreference.PreferencesManager


class StoreFragment : Fragment() {
    private lateinit var binding: FragmentStoreBinding
    private lateinit var viewModel: StoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferencesManager = PreferencesManager(requireContext())
        val storeRepository = StoreRepository((requireActivity().application as MyApplication)
            .database.getDaoStore())

        viewModel = ViewModelProvider(this,
            StoreViewModel.StoreViewModelFactory(
                storeRepository,
                preferencesManager
            ))[StoreViewModel::class.java]

        setupRecyclerView()
        observeViewModel()

        binding.addButton.setOnClickListener {
            val intent = Intent(requireContext(), AddItemAtStoreActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadItems()
    }

    private fun setupRecyclerView(){
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = MyAdapterForStore(
            emptyList(),
            { item -> // при нажатии купить
                viewModel.boughtItem(item)
            }, { item -> // кнопка активировать
                viewModel.actionTimerForStoreItem(item)
                viewModel.requestForChangeActivate(item.id)
            },{ item -> // при нажатии отмена
                viewModel.cancelItem(item)
            })
        recyclerView.adapter = adapter
        viewModel.items.observe(viewLifecycleOwner){
            adapter.updateItems(it)
        }
    }

    private fun observeViewModel(){
        viewModel.toastMessage.observe(viewLifecycleOwner){
            getToast(it)
        }

        viewModel.secondTimer.observe(viewLifecycleOwner){
            startTimer(it)
        }
    }

    private fun startTimer(seconds: Int){
        val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_LENGTH, seconds)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }
        requireContext().startActivity(intent)
    }

    private fun getToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
