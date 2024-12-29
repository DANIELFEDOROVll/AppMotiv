package com.example.newappmotiv.ui.tasks


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
import com.example.newappmotiv.databinding.FragmentTasksBinding
import com.example.newappmotiv.model.dayTasksRepository
import com.example.newappmotiv.utils.MyApplication
import com.example.newappmotiv.model.recyclerView.MyAdapter
import com.example.newappmotiv.model.recyclerView.One
import com.example.newappmotiv.model.room.DayTask
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random


class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding

    private val database by lazy {
        (requireActivity().application as MyApplication).database
    }

    lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        One.listOfTasks.clear()

        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = dayTasksRepository(database.getDaoTasks())
        preferencesManager = PreferencesManager(requireContext())

        val viewModel = ViewModelProvider(this,
            TasksViewModelFactory(repository, preferencesManager))[TasksViewModel::class.java]

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //получаем данные с бд dayTask и выводим в списке
        CoroutineScope(Dispatchers.IO).launch {
            val ts = database.getDaoTasks().getDayTasks()

            withContext(Dispatchers.Main){
                val adapter = MyAdapter(ts,
                    { t ->
                        // выполняется при нажатии "начать"
                        clickStart(t)
                    },
                    { t ->
                        // выполняется при нажатии галочки
                        viewModel.installBalance(t)
                    })
                recyclerView.adapter = adapter
            }
        }

        viewModel.toast_numer.observe(viewLifecycleOwner){ price ->
            showToastReadyTask(price)
        }

        binding.addButton.setOnClickListener {
            toAddDayTaskActivity()
        }
    }

    private fun toAddDayTaskActivity(){
        val intent = Intent(requireContext(), AddDayTasksActivity::class.java)
        startActivity(intent)
    }

    private fun addMinutesInTotalSpentTime(name: String, minutes: Int, ready: Boolean){
        if(ready) {
            CoroutineScope(Dispatchers.IO).launch {
                val spentTime = database.getDaoGeneralTasks().getTotalSpentTime(name)
                database.getDaoGeneralTasks().updateTotalSpentMinutes(name, spentTime + minutes)
            }
        }
        else{
            CoroutineScope(Dispatchers.IO).launch {
                val spentTime = database.getDaoGeneralTasks().getTotalSpentTime(name)
                database.getDaoGeneralTasks().updateTotalSpentMinutes(name, spentTime - minutes)
            }
        }
    }

    private fun showToastReadyTask(num: Float){
        val messages = listOf(
            "Молодец! Вот твои баллы!",
            "Хорош, держи свои заработаные баллы",
            "Неплохо, давай в таком же темпе.",
            "Потрудился - получил! Держи бро!"
        )
        Toast.makeText(requireContext(), messages[Random.nextInt(1,4)] + " + ${num} баллов!"
            ,Toast.LENGTH_SHORT)
            .show()
    }

    private fun clickStart(t: DayTask){
        CoroutineScope(Dispatchers.IO).launch {
            database.getDaoTasks().updateTaskInProcess(t.id, true)
        }
        val durationInMinutes = t.timeValue
        val durationInMillis = durationInMinutes * 60

        // Создаем Intent для запуска системного таймера
        val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_LENGTH, durationInMillis)
            putExtra(AlarmClock.EXTRA_MESSAGE, "Таймер для задачи: ${t.generalTaskName}")
            putExtra(AlarmClock.EXTRA_SKIP_UI, true) // Пропустить UI
        }
        startActivity(intent)
    }


    companion object{
        const val MYLOG = "my_log_1"
    }

    class TasksViewModelFactory(private val repository: dayTasksRepository,
        private val preferencesManager: PreferencesManager) : ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TasksViewModel(repository, preferencesManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}