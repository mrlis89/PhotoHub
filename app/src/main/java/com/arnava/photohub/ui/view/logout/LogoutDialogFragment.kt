package com.arnava.photohub.ui.view.logout

import android.app.Dialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.arnava.photohub.R
import com.arnava.photohub.data.local.TokenStorage
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class LogoutDialogFragment() : DialogFragment() {
    @Inject lateinit var sharedPreferences: SharedPreferences

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.dialog_logout)
                .setPositiveButton(R.string.yes) { _, _ ->
                    File(context?.cacheDir?.path).deleteRecursively()
                    clearLocalToken()
                    findNavController().navigate(R.id.action_navigation_profile_to_navigation_auth)
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun clearLocalToken() {
        TokenStorage.accessToken = ""
        TokenStorage.refreshToken = ""
        val editor = sharedPreferences.edit()
        editor.remove("access_token")
        editor.apply()
    }
}