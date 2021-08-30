package com.moon.mvvm_clean.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.moon.mvvm_clean.R
import com.moon.mvvm_clean.presentation.auth.AuthActivity
import com.moon.mvvm_clean.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runObservers()
    }

    private fun runObservers() {
        viewModel.isLogged.observe(this) {
            intent = if (it)
                Intent(this, MainActivity::class.java)
            else
                Intent(this, AuthActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(intent)
        }
    }
}