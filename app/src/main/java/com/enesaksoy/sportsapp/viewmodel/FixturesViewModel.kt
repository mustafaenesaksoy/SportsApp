package com.enesaksoy.sportsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesaksoy.sportsapp.model.FixtureResponse
import com.enesaksoy.sportsapp.model.ResultResponse
import com.enesaksoy.sportsapp.repo.SportsRepository
import com.enesaksoy.sportsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixturesViewModel @Inject constructor(private val repo : SportsRepository): ViewModel() {

    private val getResult = MutableLiveData<Resource<List<ResultResponse>>>()
    val getresult : LiveData<Resource<List<ResultResponse>>>
    get() = getResult

    fun getResult(searchString: String){
        getResult.value = Resource.loading(null)
        viewModelScope.launch {
            getResult.value = repo.getResult(searchString)
        }
    }


    private val getFixture = MutableLiveData<Resource<List<FixtureResponse>>>()
    val getfixture : LiveData<Resource<List<FixtureResponse>>>
        get() = getFixture

    fun getFixture(searchString: String){
        getFixture.value = Resource.loading(null)
        viewModelScope.launch {
            getFixture.value = repo.getFixture(searchString)
        }
    }
}