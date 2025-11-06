package com.example.gamerloopactivity.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.gamerloopactivity.R
import com.example.gamerloopactivity.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()

    }

    private fun initUI() {
        intNavigation()
    }

    private fun intNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.activityContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)
        setupBottomNavVisibility()

    }

    private fun setupBottomNavVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val destinationsWithBottomNav = setOf(
                R.id.contentScreenFragment,
                R.id.infoScreenFragment,
                R.id.settingsScreenFragment
            )
            if (destinationsWithBottomNav.contains(destination.id)) {
                binding.bottomNavView.visibility = View.VISIBLE
            } else {
                binding.bottomNavView.visibility = View.GONE
            }
        }
    }
}