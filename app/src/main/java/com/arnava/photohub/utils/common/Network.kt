package com.arnava.photohub.utils.common

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import com.arnava.photohub.App

object NetworkState {
    fun isConnected(): Boolean {
        var isConnected = true
        if (versionMOrHigher()) {
            val connectivityManager =
                (App.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            isConnected = capabilities != null
        }
        return isConnected
    }

    private fun versionMOrHigher() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    fun connectionLostToast() {
        Toast.makeText(App.appContext,"connection is lost", Toast.LENGTH_LONG).show()
    }
}