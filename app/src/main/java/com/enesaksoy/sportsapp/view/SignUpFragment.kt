package com.enesaksoy.sportsapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.enesaksoy.sportsapp.R
import com.enesaksoy.sportsapp.databinding.SignUpFragmentBinding
import com.enesaksoy.sportsapp.util.Status
import com.enesaksoy.sportsapp.viewmodel.SignsViewModel
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignUpFragment@Inject constructor(private val auth : FirebaseAuth) : Fragment(R.layout.sign_up_fragment) {
    private lateinit var binding : SignUpFragmentBinding
    private lateinit var viewModel : SignsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SignUpFragmentBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(SignsViewModel::class.java)
        binding.signUpBtn.setOnClickListener {
            if(!binding.passwordText.text.toString().equals(binding.rePasswordText.text.toString())){
                Toast.makeText(requireContext(),"passwords do not match.",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.signUp(binding.emailText.text.toString(),binding.passwordText.text.toString(),binding.nameText.text.toString())
                Toast.makeText(requireContext(),"success",Toast.LENGTH_SHORT).show()
            }
        }
        observeOn()
    }

    private fun observeOn(){
        viewModel.getsignMsg.observe(viewLifecycleOwner, Observer {
            if(it.status == Status.SUCCESS){
                if(auth.currentUser != null){
                    val action = SignUpFragmentDirections.actionSignUpFragmentToUserFragment()
                    Navigation.findNavController(requireView()).navigate(action)
                }
            }else if (it.status == Status.ERROR){
                Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}