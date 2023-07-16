package com.enesaksoy.sportsapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.enesaksoy.sportsapp.R
import com.enesaksoy.sportsapp.databinding.IntroFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class IntroFragment : Fragment(R.layout.intro_fragment) {

    @Inject
    lateinit var auth: FirebaseAuth
    private lateinit var binding: IntroFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = IntroFragmentBinding.bind(view)
        if (auth.currentUser != null){
            val action = IntroFragmentDirections.actionIntroFragmentToUserFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }
        binding.signInBtn.setOnClickListener {
            val action = IntroFragmentDirections.actionIntroFragmentToSignInFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }
        binding.signUpBtn.setOnClickListener {
            val action = IntroFragmentDirections.actionIntroFragmentToSignUpFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }
    }
}