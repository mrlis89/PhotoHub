package com.arnava.photohub.ui.view

import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.arnava.photohub.App
import com.arnava.photohub.R
import com.arnava.photohub.databinding.ActivityMainBinding
import com.arnava.photohub.ui.view.home.HomeFragment
import com.arnava.photohub.ui.view.photos.PhotoDetailsFragment
import com.arnava.photohub.utils.connection_status.ConnectivityObserver
import com.arnava.photohub.utils.connection_status.NetworkConnectivityObserver
import com.arnava.photohub.viewmodel.AuthViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var connectivityObserver: ConnectivityObserver =
        NetworkConnectivityObserver(App.appContext)
    private val authViewModel: AuthViewModel by viewModels()
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel.intentExternalData = intent?.data
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (authViewModel.isFirstRun()) binding.onboardingLayout.isVisible = true
        val stringList = resources.getStringArray(R.array.onboarding_phrases)
        with(binding) {
            when (currentPage) {
                0 -> onboardingText.text = stringList[0]
                1 -> onboardingText.text = stringList[1]
                2 -> onboardingText.text = stringList[2]
            }
        }
        binding.nextPageBtn.setOnClickListener {
            if (currentPage == 0) binding.prevPageBtn.isVisible = true
            if (currentPage < 2) currentPage++
            if (currentPage == 2) binding.onboardingLayout.isVisible = false
        }
        binding.prevPageBtn.setOnClickListener {
            if (currentPage > 0) currentPage--
            if (currentPage == 0) binding.prevPageBtn.isVisible = false
        }

        lifecycleScope.launchWhenStarted {
            connectivityObserver.observe().collect {
                when (it) {
                    ConnectivityObserver.Status.Available ->
                        Toast.makeText(
                        this@MainActivity,
                        "connection available",
                        Toast.LENGTH_LONG
                    ).show()
                    ConnectivityObserver.Status.Unavailable -> Toast.makeText(
                        this@MainActivity,
                        "connection unavailable",
                        Toast.LENGTH_LONG
                    ).show()
                    ConnectivityObserver.Status.Losing -> Toast.makeText(
                        this@MainActivity,
                        "connection losing",
                        Toast.LENGTH_LONG
                    ).show()
                    ConnectivityObserver.Status.Lost -> {
                        Toast.makeText(this@MainActivity, "connection lost", Toast.LENGTH_LONG)
                            .show()
                        navController.navigate(R.id.db_photos_fragment)
                    }
                }

            }
        }
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                }
                R.id.navigation_collections -> {
                    navController.navigate(R.id.navigation_collections)
                }
                R.id.navigation_profile -> {
                    navController.navigate(R.id.navigation_profile)
                }
            }
            true
        }
    }

}
