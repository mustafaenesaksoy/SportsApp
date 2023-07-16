package com.enesaksoy.sportsapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.enesaksoy.sportsapp.R
import com.enesaksoy.sportsapp.databinding.UserFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserFragment @Inject constructor(private val auth: FirebaseAuth): Fragment(R.layout.user_fragment) {
    private lateinit var binding : UserFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = UserFragmentBinding.bind(view)

        auth.currentUser?.let {
            binding.userName.text = auth.currentUser!!.displayName
        }
        binding.logOutTextView.setOnClickListener {
            auth.signOut()
            findNavController().popBackStack()
        }
    }
}