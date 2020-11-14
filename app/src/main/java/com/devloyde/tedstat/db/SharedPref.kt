package com.devloyde.tedstat.db

import android.content.Context
import android.content.SharedPreferences

class SharedPref(val context: Context) {
    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("ted_stat_preferences", Context.MODE_PRIVATE)

    private val welcomeKey: String = "FIRST_LAUNCH"
    private val darkModeKey: String = "DARK_MODE"
    private val selectedCountryKey: String = "DASHBOARD_SELECTED_COUNTRY"

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

    //SAVE DASHBOARD SELECTED COUNTRY
    fun saveDashboardSelectedCountry(country: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(selectedCountryKey, country)
        editor.apply()
    }

    //LOAD FIRST LAUNCH STATE
    fun loadLaunchState(): Boolean {
        return sharedPreferences.getBoolean(welcomeKey, false)
    }

    //LOAD DASHBOARD SELECTED COUNTRY
    fun loadDashboardSelectedCountry(): String? {
        return sharedPreferences.getString(selectedCountryKey, "Nigeria")
    }

    //LOAD DARK MODE STATE
    fun loadDarkModeState(): Boolean {
        return sharedPreferences.getBoolean(darkModeKey, false)
    }
}