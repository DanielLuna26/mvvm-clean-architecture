package com.moon.mvvm_clean.presentation.auth.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.moon.mvvm_clean.R
import com.moon.mvvm_clean.databinding.FragmentSignUpBinding
import com.moon.mvvm_clean.utils.delegate.viewBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}