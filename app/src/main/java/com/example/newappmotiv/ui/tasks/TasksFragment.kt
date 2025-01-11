package com.example.newappmotiv.ui.tasks


import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newappmotiv.databinding.FragmentTasksBinding
import com.example.newappmotiv.model.recyclerView.MyAdapter
import com.example.newappmotiv.model.recyclerView.One

import org.koin.androidx.viewmodel.ext.android.viewModel


class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private val viewModel: TasksViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    /*private fun animateCups(cupCount: Int) {
        for (i in 1..cupCount) {
            val animatedCup = TextView(requireContext()).apply {
                text = "$i" // Текущее значение кубка
                textSize = 20f
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }

            // Добавляем TextView в родительский layout
            (view?.findViewById<ConstraintLayout>(R.id.recyclerView))?.addView(animatedCup)

            // Устанавливаем начальные координаты для анимации
            animatedCup.x = cupTextView.x + cupTextView.width / 2 - animatedCup.width / 2
            animatedCup.y = cupTextView.y + cupTextView.height / 2 - animatedCup.height / 2

            // Конечные координаты (позиция кнопки)
            val targetX = bottomNavigationView.x + bottomNavigationView.width / 2 - animatedCup.width / 2
            val targetY = bottomNavigationView.y - animatedCup.height

            // Анимация перемещения по X
            ObjectAnimator.ofFloat(animatedCup, "translationX", targetX - animatedCup.x).apply {
                duration = 500 // Длительность анимации в миллисекундах
                start() // Запуск анимации
            }

            // Анимация перемещения по Y
            ObjectAnimator.ofFloat(animatedCup, "translationY", targetY - animatedCup.y).apply {
                duration = 500 // Длительность анимации в миллисекундах
                start() // Запуск анимации
            }

            // Удаляем TextView после завершения анимации через 500 мс
            animatedCup.postDelayed({
                (findViewById<RelativeLayout>(R.id.rootLayout)).removeView(animatedCup)
            }, 500)
        }
    } */
}