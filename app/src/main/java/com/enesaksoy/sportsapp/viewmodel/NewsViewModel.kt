package com.enesaksoy.sportsapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesaksoy.sportsapp.model.NewsResponseItem
import com.enesaksoy.sportsapp.repo.SportsRepository
import com.enesaksoy.sportsapp.util.CustomSharedPreferences
import com.enesaksoy.sportsapp.util.Resource
import com.enesaksoy.sportsapp.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repo : SportsRepository,val application : Application): ViewModel(){

    private val newslist = MutableLiveData<Resource<List<NewsResponseItem>>>()
    val newsList : LiveData<Resource<List<NewsResponseItem>>>
    get() = newslist

    private val selectedNew = MutableLiveData<NewsResponseItem>()
    val selectednew : LiveData<NewsResponseItem>
    get() = selectedNew

    fun setSelectedNew(newsResponseItem: NewsResponseItem){
        selectedNew.value = newsResponseItem
    }

    private var customPreferences = CustomSharedPreferences(application)
    private val refreshTime = 10 * 60 * 1000 * 1000 * 1000L
    fun refreshdata(searchString: String){
        val updateTime = customPreferences.gettime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSqlite()
        }else {
            getNewsList(searchString)
        }
    }


    fun getNewsList(searchString : String){
        if (searchString.isEmpty()){
            return
        }
        newslist.value = Resource.loading(null)
        viewModelScope.launch {
            val resource = repo.getNews(searchString)
            if(resource.status == Status.SUCCESS){
                repo.deleteAllNews()
                subscribeToRoom(resource.data!!)
            }else if(resource.status == Status.ERROR){
                newslist.value = Resource.error(resource.message!!,null)
            }
        }
    }

    fun subscribeToRoom(response : List<NewsResponseItem>){
        viewModelScope.launch {
            if(response != null){
                repo.insertAllNews(response)
                showNews(response)
                Toast.makeText(application,"API",Toast.LENGTH_SHORT).show()
                customPreferences.savetime(System.nanoTime())
            }
        }
    }

    fun getDataFromSqlite(){
        viewModelScope.launch {
            showNews(repo.getAllNews())
            Toast.makeText(application,"SQLite",Toast.LENGTH_SHORT).show()
        }
    }

    fun showNews(response: List<NewsResponseItem>){
        newslist.value = Resource.success(response)
    }
}