package com.moon.mvvm_clean.presentation.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.moon.mvvm_clean.R
import com.moon.mvvm_clean.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    private lateinit var navController: NavController

    private var actionToggleMode: MenuItem? = null

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.auth_nav_container) as NavHostFragment

        navController = navHostFragment.navController

        setSupportActionBar(binding.authToolbar)

        supportActionBar?.setDisplayShowTitleEnabled(true)

        setupActionBarWithNavController(navController, null)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.auth_toolbar_menu, menu)

        menu?.let {
            actionToggleMode = menu.findItem(R.id.action_toggle_mode)

            actionToggleMode?.setOnMenuItemClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.userPreferences.toggleNightMode()
                }

                return@setOnMenuItemClickListener true
            }

            lifecycleScope.launch {
                viewModel.userPreferences.isNightMode.collect { isNightMode ->
                    if (isNightMode) {
                        actionToggleMode?.icon = getDrawable(this@AuthActivity, R.drawable.ic_dark)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        val draw = getDrawable(this@AuthActivity, R.drawable.ic_light)
                        draw?.setTint(resources.getColor(R.color.white, null))
                        actionToggleMode?.icon = draw
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

}