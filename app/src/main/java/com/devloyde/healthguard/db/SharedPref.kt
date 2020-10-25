package com.devloyde.healthguard.db

import android.content.Context
import android.content.SharedPreferences

class SharedPref(val context: Context) {
    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("ted_stat_preferences", Context.MODE_PRIVATE)

    private val welcomeKey: String = "FIRST_LAUNCH"
    private val darkModeKey: String = "DARK_MODE"

    //SAVE FIRST LAUNCH STATE
    fun setLaunchState(state: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(welcomeKey, state)
        editor.apply()
    }

    //SAVE DARK MODE STATE
    fun setDarkModeState(state: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(darkModeKey, state)
        editor.apply()
    }

    //LOAD FIRST LAUNCH STATE
    fun loadLaunchState(): Boolean {
        return sharedPreferences.getBoolean(welcomeKey, false)
    }

    //LOAD DARK MODE STATE
    fun loadDarkModeState(): Boolean {
        return sharedPreferences.getBoolean(darkModeKey, false)
    }
}