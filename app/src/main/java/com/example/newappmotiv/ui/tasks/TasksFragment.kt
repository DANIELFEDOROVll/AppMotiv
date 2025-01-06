package com.example.newappmotiv.ui.tasks


import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newappmotiv.databinding.FragmentTasksBinding
import com.example.newappmotiv.model.DayTasksRepository
import com.example.newappmotiv.model.GeneralTasksRepository
import com.example.newappmotiv.utils.MyApplication
import com.example.newappmotiv.model.recyclerView.MyAdapter
import com.example.newappmotiv.model.recyclerView.One
import com.example.newappmotiv.model.recyclerView.TaskForRecycler
import com.example.newappmotiv.model.sharedPreference.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private lateinit var viewModel: TasksViewModel
    private val database by lazy {
        (requireActivity().application as MyApplication).database
    }
    //val viewModel: TasksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dayTasksRepository = DayTasksRepository(database.getDaoTasks())
        val generalTasksRepository = GeneralTasksRepository(database.getDaoGeneralTasks())
        val preferencesManager = PreferencesManager(requireContext())

        viewModel = ViewModelProvider(this,
            TasksViewModel.TasksViewModelFactory(
                dayTasksRepository,
                generalTasksRepository,
                preferencesManager)
        )[TasksViewModel::class.java]

        setupRecyclerView()
        observeViewModel()

        binding.addButton.setOnClickListener {
            toAddDayTaskActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadTasks()
        One.listOfTasks.clear()
    }

    private fun setupRecyclerView(){
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = MyAdapter(
            emptyList(),
            { t ->
                // выполняется при нажатии "начать"
                viewModel.clickStart(t)
            },
            { t ->
                //выполняется при нажатии на галочку
                viewModel.installBalance(t)
            })
        recyclerView.adapter = adapter

        viewModel.tasks.observe(viewLifecycleOwner){
            adapter.updateTasks(it)
        }
    }

    private fun observeViewModel(){
        viewModel.toast_message.observe(viewLifecycleOwner){
            showToastReadyTask(it)
        }

        viewModel.secondTimer.observe(viewLifecycleOwner){
            startTimer(it)
        }
    }

    private fun toAddDayTaskActivity(){
        val intent = Intent(requireContext(), AddDayTasksActivity::class.java)
        startActivity(intent)
    }

    private fun showToastReadyTask(message: String){
        Toast.makeText(requireContext(), message,Toast.LENGTH_SHORT).show()
    }

    private fun startTimer(seconds: Int){
        // Intent для запуска системного таймера
        val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_LENGTH, seconds)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true) // Пропустить UI
        }
        startActivity(intent)
    }

    companion object{
        const val MYLOG = "my_log_1"
    }
}