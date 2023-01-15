package com.arnava.photohub.data.repository

import android.content.SharedPreferences
import javax.inject.Inject

private const val KEY = "access_token"
class LocalRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {
    private val editor = sharedPreferences.edit()

    fun getTokenFromSharedPrefs(): String {
        return sharedPreferences.getString(KEY, "").toString() // empty value by default
    }

    fun saveTokenToSharedPref(text: String) {
        editor.putString(KEY, text)
        editor.apply()
    }
}