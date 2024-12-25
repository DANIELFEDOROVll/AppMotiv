package com.example.newappmotiv.ui.tasks


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.AlarmClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.example.newappmotiv.databinding.FragmentTasksBinding
import com.example.newappmotiv.model.MyApplication
import com.example.newappmotiv.model.recyclerView.MyAdapter
import com.example.newappmotiv.model.recyclerView.TaskForRecycler
import com.example.newappmotiv.model.room.DayTask
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

// сделать анимацию добавления баллов и добавить таймер к задачам и покупкам если это необходимо
// сделать корзину в store, где можно активировать таймер покупки

class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding

    private val database by lazy {
        (requireActivity().application as MyApplication).database
    }

    private lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferencesManager = PreferencesManager(requireContext())

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        
        /*viewModel.tasks.observe(viewLifecycleOwner){ tasks ->
            val adapter = MyAdapter(tasks,
                { t ->
                    //выполняется при нажатии "начать"
                    clickStart(t)
                },
                { t ->
                    // выполняется при нажатии галочки
                    installBalance(t)

                })
            recyclerView.adapter = adapter
        }*/

        //получаем данные с бд dayTask и выводим в списке
        CoroutineScope(Dispatchers.IO).launch {
            val ts = database.getDaoTasks().getDayTasks()

            withContext(Dispatchers.Main){
                val adapter = MyAdapter(ts,
                    { t ->
                        //выполняется при нажатии "начать"
                        clickStart(t)
                    },
                    { t ->
                        // выполняется при нажатии галочки
                        installBalance(t)
                    })
                recyclerView.adapter = adapter
            }
        }

        binding.addButton.setOnClickListener {
            toAddDayTaskActivity()
        }
    }

    private fun toAddDayTaskActivity(){
        val intent = Intent(requireContext(), AddDayTasksActivity::class.java)
        startActivity(intent)
    }

    private fun installBalance(t: DayTask){// меняет в сущности ready и считает баллы за задание
        CoroutineScope(Dispatchers.IO).launch {
            database.getDaoTasks().updateTaskReadyById(t.id, t.ready) //меняем ready в сущности
            if(t.ready){ // при выполнении задания
                preferencesManager.updateNowBalanceForTasks(preferencesManager.getNowBalance() +
                        t.price)
                withContext(Dispatchers.Main){
                    showToastReadyTask(t.price)
                }
            }
            if(!t.ready){ // при отмене задания
                preferencesManager.updateNowBalanceForTasks(preferencesManager.getNowBalance() -
                        t.price)
            }
            addMinutesInTotalSpentTime(t.name, t.timeValue, t.ready)
        }
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
            putExtra(AlarmClock.EXTRA_MESSAGE, "Таймер для задачи: ${t.name}")
            putExtra(AlarmClock.EXTRA_SKIP_UI, true) // Пропустить UI
        }
        startActivity(intent)
    }


    companion object{
        const val MYLOG = "my_log_1"
    }
}