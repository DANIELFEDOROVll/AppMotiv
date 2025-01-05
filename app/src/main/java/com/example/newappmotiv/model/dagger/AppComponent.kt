package com.example.newappmotiv.model.dagger

import com.example.newappmotiv.ui.tasks.TasksFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(tasksFragment: TasksFragment)
}