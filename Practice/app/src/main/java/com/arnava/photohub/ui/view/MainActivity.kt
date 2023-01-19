package com.arnava.photohub.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.arnava.photohub.R
import com.arnava.photohub.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
//        navView.setupWithNavController(navController)

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
