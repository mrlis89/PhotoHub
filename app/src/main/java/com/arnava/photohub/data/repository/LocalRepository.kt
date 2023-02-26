package com.arnava.photohub.data.repository

import android.content.SharedPreferences
import javax.inject.Inject

private const val KEY = "access_token"
private const val FIRST_RUN = "first_run"
class LocalRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {
    private val editor = sharedPreferences.edit()

    fun getTokenFromSharedPrefs(): String {
        return sharedPreferences.getString(KEY, "").toString() // empty value by default
    }

    fun saveTokenToSharedPref(text: String) {
        editor.putString(KEY, text)
        editor.apply()
    }

    fun isFirstRun(): Boolean {
        if (sharedPreferences.getBoolean(FIRST_RUN, false)) return false
        editor.putBoolean(FIRST_RUN, true)
        editor.apply()
        return true
    }
}