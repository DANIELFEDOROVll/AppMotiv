package com.example.newappmotiv.ui.store

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newappmotiv.databinding.FragmentStoreBinding
import com.example.newappmotiv.utils.MyApplication
import com.example.newappmotiv.model.recyclerView.MyAdapterForStore
import com.example.newappmotiv.model.room.StoresItem
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import com.example.newappmotiv.ui.MainActivity
import com.example.newappmotiv.ui.tasks.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StoreFragment : Fragment() {
    private lateinit var binding: FragmentStoreBinding
    private val database by lazy {
        (requireActivity().application as MyApplication).database
    }

    private lateinit var preferencesManager: PreferencesManager
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
        preferencesManager = PreferencesManager(requireContext())

        viewModel = (activity as MainActivity).getStoreViewModel()

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = MyAdapterForStore(
            emptyList(),
            { item -> //при нажатии купить
                viewModel.boughtItem(item)
            }, { item -> // анопка активировать
                viewModel.actionTimerForStoreItem(item)
                viewModel.requestForChangeActivate(item.id)
            },{ item -> //при нажатии отмена
                viewModel.cancelItem(item)
            })
        recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner){
            adapter.updateItems(it)
        }

        viewModel.toastMessage.observe(viewLifecycleOwner){
            getToast(it)
        }

        viewModel.secondTimer.observe(viewLifecycleOwner){
            startTimer(it)
        }

        binding.addButton.setOnClickListener {
            val intent = Intent(requireContext(), AddItemAtStoreActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadItems()
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
