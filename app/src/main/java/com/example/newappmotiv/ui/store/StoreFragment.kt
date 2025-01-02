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
    private lateinit var tasksViewModel: TasksViewModel
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
        tasksViewModel = (activity as MainActivity).getTasksViewModel()

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = MyAdapterForStore(
            emptyList(),
            { item -> //при нажатии купить
                boughtItem(item)
            }, { item -> // анопка активировать
                actionTimerForStoreItem(item)
                requestForChangeActivate(item)
            },{ item -> //при нажатии отмена
                cancelItem(item)
            })
        recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner){
            adapter.updateItems(it)
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

    private fun boughtItem(item: StoresItem): Boolean{
        val work = preferencesManager.updateNowBalanceForBuy(item.price)
        if(!work) {
            Toast.makeText(requireContext(), "Не хватает средств!", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        else{
            CoroutineScope(Dispatchers.IO).launch {
                database.getDaoStore().updateTaskReadyById(item.id, item.bought)
            }
            showToastBuyOrCancelItem(item.price, true)
            preferencesManager.updateSpentAllTime(item.price)
            return true
        }
    }

    private fun cancelItem(item: StoresItem){
        CoroutineScope(Dispatchers.IO).launch {
            database.getDaoStore().updateTaskReadyById(item.id, item.bought)
        }
        preferencesManager.updateNowBalanceForCancel(item.price)
        showToastBuyOrCancelItem(item.price, false)
    }

    private fun showToastBuyOrCancelItem(num: Float, buy: Boolean){
        var text: String
        if(buy) text = "Потраченно на покупку ${num} баллов"
        else text = "Покупка отменена, баллы возвращенны"
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT)
            .show()
    }

    private fun actionTimerForStoreItem(item: StoresItem){
        val durationInMinutes = item.timeValue
        val durationInSecond = durationInMinutes * 60

        // Создаем Intent для запуска системного таймера
        val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_LENGTH, durationInSecond)
            putExtra(AlarmClock.EXTRA_MESSAGE, "Таймер для покупки: ${item.name}")
            putExtra(AlarmClock.EXTRA_SKIP_UI, true) // Пропустить UI
        }
        requireContext().startActivity(intent)
    }

    fun requestForChangeActivate(item: StoresItem){
        CoroutineScope(Dispatchers.IO).launch {
            database.getDaoStore().updateIsAlreadyActive(item.id, true)
        }
    }
}
