package com.enesaksoy.sportsapp.repo

import com.enesaksoy.sportsapp.service.RetrofitApi
import com.enesaksoy.sportsapp.model.*
import com.enesaksoy.sportsapp.service.NewsDao
import com.enesaksoy.sportsapp.util.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import retrofit2.Response
import javax.inject.Inject

class SportsRepositoryImpl @Inject constructor(
    private val retrofitApi: RetrofitApi,
    private val newsDao: NewsDao,
    private val auth : FirebaseAuth
    ) : SportsRepository {
    override suspend fun getNews(searchString: String): Resource<List<NewsResponseItem>> {
        return try {
            val response = retrofitApi.getNews(searchString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error.", null)
            } else {
                Resource.error("No data!", null)
            }
        } catch (e: Exception) {
            Resource.error(e.localizedMessage, null)
        }
    }

    override suspend fun getResult(searchString: String): Resource<List<ResultResponse>> {
        return try {
            val incomingData = retrofitApi.getResult(searchString)
            if (incomingData.isSuccessful) {
                incomingData.body()?.let {
                    val resultList = ArrayList<ResultResponse>()
                    for (i in it){
                        val mapValues = i.values
                        for (mapValue in mapValues){
                            resultList.addAll(mapValue)
                        }
                    }
                    return@let Resource.success(resultList.reversed())
                } ?: Resource.error("Error!",null)
            }else{
                Resource.error("No data!",null)
            }
        } catch (e: Exception) {
            Resource.error(e.localizedMessage, null)
        }
    }

    override suspend fun getFixture(searchString: String): Resource<List<FixtureResponse>> {
        return try {
            val incomingData = retrofitApi.getFixture(searchString)
            if (incomingData.isSuccessful) {
                incomingData.body()?.let {
                    val resultList = ArrayList<FixtureResponse>()
                    for (i in it){
                        val mapValues = i.values
                        for (mapValue in mapValues){
                            resultList.addAll(mapValue)
                        }
                    }
                    return@let Resource.success(resultList)
                } ?: Resource.error("Error!",null)
            }else{
                Resource.error("No data!",null)
            }
        } catch (e: Exception) {
            Resource.error(e.localizedMessage, null)
        }
    }

    override suspend fun getTable(searchString: String): Resource<List<TableResponseItem>> {
        return try {
            val response = retrofitApi.getTable(searchString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("No Data!", null)
            }
        } catch (e: Exception) {
            Resource.error(e.localizedMessage, null)
        }
    }

    override suspend fun insertAllNews(newsList: List<NewsResponseItem>) {
        newsDao.insertAllNews(*newsList.toTypedArray())
    }

    override suspend fun deleteAllNews() {
        newsDao.deleteAllNews()
    }

    override suspend fun getAllNews(): List<NewsResponseItem> {
        return newsDao.getAllNews()
    }

    override suspend fun signIn(email: String, password: String): Resource<AuthResult> {
        if (email.equals("") || password.equals("")) {
            return Resource.error("Email and password cannot be left blank.", null)
        }
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Resource.success(result)
        } catch (e: Exception) {
            Resource.error("User Error", null)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        userName: String
    ): Resource<AuthResult> {
        if (email.equals("") || password.equals("") || userName.equals("")) {
            return Resource.error("Username,Email and password cannot be left blank.", null)
        }
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            auth.currentUser.let { user ->
                val profileUpdate = UserProfileChangeRequest.Builder()
                    .setDisplayName(userName)
                    .build()
                user?.updateProfile(profileUpdate)?.await()
                return@let Resource.success(result)
            }
        } catch (e: Exception) {
            Resource.error("User Error", null)
        }
    }
}

