package com.example.newappmotiv.model.sharedPreference

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME_KEY, Context.MODE_PRIVATE)

    init {
        if (isFirstRun()) {
            initializeBalance()
        }
    }

    private fun isFirstRun(): Boolean {
        val isFirstRun = sharedPreferences.getBoolean(IS_FIRST_RUN_KEY, true)
        if (isFirstRun) {
            sharedPreferences.edit().putBoolean(IS_FIRST_RUN_KEY, false).apply()
        }
        return isFirstRun
    }

    private fun initializeBalance() {
        sharedPreferences.edit().putFloat(BALANCE_NOW_KEY, 0.0f).apply()
        sharedPreferences.edit().putFloat(BALANCE_ALLTIME_KEY, 0.0f).apply()
        sharedPreferences.edit().putFloat(COSTS_ALLTIME_KEY, 0.0f).apply()
    }

    fun getNowBalance(): Float {
        return sharedPreferences.getFloat(BALANCE_NOW_KEY, 0.0f)
    }

    fun updateNowBalanceForStore(newBalance: Float): Boolean {
        if (newBalance >= 0) {
            sharedPreferences.edit().putFloat(BALANCE_NOW_KEY, newBalance).apply()
            return true
        }
        return false
    }

    fun updateNowBalanceForTasks(newBalance: Float) {
        sharedPreferences.edit().putFloat(BALANCE_NOW_KEY, newBalance).apply()
    }

    companion object{
        // ключи для sharedPreference
        const val SHARED_PREF_NAME_KEY = "shered_pref_one"
        const val BALANCE_NOW_KEY = "balance_now_key" //ключ текущего балланса
        const val BALANCE_ALLTIME_KEY = "ballance_all_time_key" //балланс за все время
        const val COSTS_ALLTIME_KEY = "costs_all_time_key" //потрачено за все время
        private val IS_FIRST_RUN_KEY = "is_first_run"
    }
}