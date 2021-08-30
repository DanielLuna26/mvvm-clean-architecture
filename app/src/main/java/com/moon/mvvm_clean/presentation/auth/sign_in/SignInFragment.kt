package com.moon.mvvm_clean.presentation.auth.sign_in

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.moon.mvvm_clean.R
import com.moon.mvvm_clean.databinding.FragmentSignInBinding
import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.presentation.main.MainActivity
import com.moon.mvvm_clean.utils.delegate.viewBinding
import com.moon.mvvm_clean.utils.handleApiErrors
import com.moon.mvvm_clean.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.singInBtnSend.setOnClickListener {
            if (viewModel.isFormValid())
                viewModel.onClickSignIn()
            else
                formWatchers()
        }

        binding.signInEdtxtPassword.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onClickSignIn()
                true
            } else
                false
        }

        binding.signInBtnSignUp.setOnClickListener {
            val action = SignInFragmentDirections
                .actionNavigationSignInToNavigationSignUp()
            findNavController().navigate(action)
        }

        runObservers()
    }

    private fun runObservers() = viewLifecycleOwner.lifecycleScope.launch {

        viewModel.result.collect {
            val result = it ?: return@collect

            when (result) {
                is Resource.Success -> {
                    Intent(requireActivity(), MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(this)
                    }
                    viewModel.resetResult()
                }
                is Resource.Failure -> {
                    handleApiErrors(result) { viewModel.onClickSignIn() }
                    viewModel.resetResult()
                }
            }
        }

    }

    private fun formWatchers() {
        binding.singInBtnSend.isEnabled = false

        viewModel.password.asLiveData().observe(viewLifecycleOwner) {
            binding.singInBtnSend.isEnabled = viewModel.isFormValid()
        }

        viewModel.email.asLiveData().observe(viewLifecycleOwner) {
            binding.singInBtnSend.isEnabled = viewModel.isFormValid()
        }

    }
}