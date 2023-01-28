package com.arnava.photohub.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.arnava.photohub.App
import com.arnava.photohub.R
import com.arnava.photohub.databinding.ActivityMainBinding
import com.arnava.photohub.utils.connection_status.ConnectivityObserver
import com.arnava.photohub.utils.connection_status.NetworkConnectivityObserver
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var connectivityObserver: ConnectivityObserver =
        NetworkConnectivityObserver(App.appContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        lifecycleScope.launchWhenStarted {
            connectivityObserver.observe().collect {
                when (it) {
                    ConnectivityObserver.Status.Available -> Toast.makeText(
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
