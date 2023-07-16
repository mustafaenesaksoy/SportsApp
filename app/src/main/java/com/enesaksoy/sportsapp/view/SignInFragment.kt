package com.enesaksoy.sportsapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.enesaksoy.sportsapp.R
import com.enesaksoy.sportsapp.databinding.SignInFragmentBinding
import com.enesaksoy.sportsapp.util.Status
import com.enesaksoy.sportsapp.viewmodel.SignsViewModel
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignInFragment @Inject constructor(private val auth : FirebaseAuth): Fragment(R.layout.sign_in_fragment) {
    private lateinit var binding : SignInFragmentBinding
    private lateinit var viewModel : SignsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SignInFragmentBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(SignsViewModel::class.java)
        binding.signInBtn.setOnClickListener {
            viewModel.signIn(binding.emailText.text.toString(),binding.passwordText.text.toString())
        }
        observeOn()
    }

    private fun observeOn(){
        viewModel.getsignMsg.observe(viewLifecycleOwner, Observer {
            if(it.status == Status.SUCCESS){
                if(auth.currentUser != null){
                    val action = SignInFragmentDirections.actionSignInFragmentToUserFragment()
                    Navigation.findNavController(requireView()).navigate(action)
                }
            }else if (it.status == Status.ERROR){
                Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}