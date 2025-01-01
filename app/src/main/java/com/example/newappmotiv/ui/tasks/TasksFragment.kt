package com.example.newappmotiv.ui.tasks


import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newappmotiv.model.GeneralTasksRepository
import com.example.newappmotiv.databinding.FragmentTasksBinding
import com.example.newappmotiv.model.DayTasksRepository
import com.example.newappmotiv.utils.MyApplication
import com.example.newappmotiv.model.recyclerView.MyAdapter
import com.example.newappmotiv.model.recyclerView.One
import com.example.newappmotiv.model.room.DayTask
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import com.example.newappmotiv.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random


class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding

    val database by lazy {
        (requireActivity().application as MyApplication).database
    }

    private lateinit var viewModel: TasksViewModel
    private lateinit var adapter: MyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).getTasksViewModel()

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = MyAdapter(
            emptyList(),
            { t ->
                // выполняется при нажатии "начать"
                clickStart(t)
            },
            { t -> //выполняется при нажатии на галочку
                viewModel.installBalance(t)
            })
        recyclerView.adapter = adapter

        viewModel.tasks.observe(viewLifecycleOwner, Observer { items ->
            items?.let {
                adapter.updateTasks(it)
            }
        })

        viewModel.toast_message.observe(viewLifecycleOwner){
            showToastReadyTask(it)
        }

        binding.addButton.setOnClickListener {
            toAddDayTaskActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadTasks()
    }

    private fun toAddDayTaskActivity(){
        val intent = Intent(requireContext(), AddDayTasksActivity::class.java)
        startActivity(intent)
    }

    private fun showToastReadyTask(message: String){
        Toast.makeText(requireContext(), message,Toast.LENGTH_SHORT).show()
    }

    private fun clickStart(t: DayTask){
        CoroutineScope(Dispatchers.IO).launch {
            database.getDaoTasks().updateTaskInProcess(t.id, true)
        }
        val durationInMinutes = t.timeValue
        val durationInMillis = durationInMinutes * 60

        // Intent для запуска системного таймера
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
}