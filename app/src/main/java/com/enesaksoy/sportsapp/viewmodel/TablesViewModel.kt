package com.enesaksoy.sportsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesaksoy.sportsapp.model.TableResponseItem
import com.enesaksoy.sportsapp.repo.SportsRepository
import com.enesaksoy.sportsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TablesViewModel @Inject constructor(private val repo : SportsRepository): ViewModel(){

    private val getTableList = MutableLiveData<Resource<List<TableResponseItem>>>()
    val gettableList : MutableLiveData<Resource<List<TableResponseItem>>>
    get() = getTableList

    fun getTableList(searchString: String){
        getTableList.value = Resource.loading(null)
        viewModelScope.launch {
            getTableList.value = repo.getTable(searchString)
        }
    }
}