package com.example.newappmotiv.DI

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.newappmotiv.ui.profile.ProfileFragment
import com.example.newappmotiv.ui.store.StoreFragment
import com.example.newappmotiv.ui.tasks.TasksFragment
import javax.inject.Inject

class AppFragmentFactory @Inject constructor(
    private val tasksFragment: TasksFragment,
    private val storeFragment: StoreFragment,
    private val profileFragment: ProfileFragment
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            TasksFragment::class.java.name -> tasksFragment
            StoreFragment::class.java.name -> storeFragment
            ProfileFragment::class.java.name -> profileFragment
            else -> super.instantiate(classLoader, className)
        }
    }
}