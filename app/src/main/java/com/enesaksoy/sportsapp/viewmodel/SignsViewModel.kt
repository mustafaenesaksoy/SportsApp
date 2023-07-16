package com.enesaksoy.sportsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesaksoy.sportsapp.repo.SportsRepository
import com.enesaksoy.sportsapp.util.Resource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignsViewModel @Inject constructor(private val repo : SportsRepository): ViewModel() {

    private val getSignMsg = MutableLiveData<Resource<AuthResult>>()
    val getsignMsg : LiveData<Resource<AuthResult>>
        get() = getSignMsg

    fun signIn(email : String, password : String){
        viewModelScope.launch {
            getSignMsg.value = repo.signIn(email, password)
        }
    }

    fun signUp(email : String, password : String,userName : String){
        viewModelScope.launch {
            getSignMsg.value = repo.signUp(email, password,userName)
        }
    }
}