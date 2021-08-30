package com.moon.mvvm_clean.presentation.auth.sign_up

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.moon.mvvm_clean.R
import com.moon.mvvm_clean.databinding.FragmentSignUpBinding
import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.presentation.main.MainActivity
import com.moon.mvvm_clean.utils.delegate.viewBinding
import com.moon.mvvm_clean.utils.handleApiErrors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    private val viewModel : SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.singUpBtnSend.setOnClickListener {
            if (viewModel.isFormValid())
                viewModel.onClickSignUp()
            else
                formWatchers()
        }

        runObservers()
    }

    private fun runObservers() {
        viewModel.result.observe(viewLifecycleOwner) {
            val result = it ?: return@observe

            when (result) {
                is Resource.Success -> {
                    Intent(requireActivity(), MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(this)
                    }
                    viewModel.resetResult()
                }
                is Resource.Failure -> {
                    handleApiErrors(result) { viewModel.onClickSignUp() }
                    viewModel.resetResult()
                }
            }
        }
    }

    private fun formWatchers() {
        viewModel.password.observe(viewLifecycleOwner) {
            binding.singUpBtnSend.isEnabled = viewModel.isFormValid()
        }
        viewModel.email.observe(viewLifecycleOwner) {
            binding.singUpBtnSend.isEnabled = viewModel.isFormValid()
        }
        viewModel.confirmPassword.observe(viewLifecycleOwner) {
            binding.singUpBtnSend.isEnabled = viewModel.isFormValid()
        }
    }
}