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
        sharedPreferences.edit().putFloat(SPENT_ALLTIME_KEY, 0.0f).apply()
    }

    fun getNowBalance(): Float {
        return sharedPreferences.getFloat(BALANCE_NOW_KEY, -1f)
    }

    fun getAllTimeBalance(): Float{
        return sharedPreferences.getFloat(BALANCE_ALLTIME_KEY, -1f)
    }

    fun updateNowBalanceForStore(minus: Float): Boolean {
        val newBalance = getNowBalance() - minus
        if (newBalance >= 0) {
            sharedPreferences.edit().putFloat(BALANCE_NOW_KEY, newBalance).apply()
            return true
        }
        return false
    }

    fun updateNowBalanceForTasks(plus: Float) {
        val newBalance = getNowBalance() + plus
        sharedPreferences.edit().putFloat(BALANCE_NOW_KEY, newBalance).apply()
        updateAllTimeBalance(plus)
    }

    private fun updateAllTimeBalance(plus: Float){
        sharedPreferences.edit().putFloat(BALANCE_ALLTIME_KEY, getAllTimeBalance() + plus)
    }

    fun getSpentAllTime(): Float{
        return sharedPreferences.getFloat(SPENT_ALLTIME_KEY, -1f)
    }

    fun updateSpentAllTime(plus: Float){
        val newB = getSpentAllTime() + plus
        sharedPreferences.edit().putFloat(SPENT_ALLTIME_KEY, newB)
    }

    companion object{
        // ключи для sharedPreference
        const val SHARED_PREF_NAME_KEY = "shered_pref_one"
        const val BALANCE_NOW_KEY = "balance_now_key" //ключ текущего балланса
        const val BALANCE_ALLTIME_KEY = "ballance_all_time_key" //балланс за все время
        const val SPENT_ALLTIME_KEY = "costs_all_time_key" //потрачено за все время
        private val IS_FIRST_RUN_KEY = "is_first_run"
    }
}