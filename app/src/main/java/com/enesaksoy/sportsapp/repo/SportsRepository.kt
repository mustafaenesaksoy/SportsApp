package com.enesaksoy.sportsapp.repo

import com.enesaksoy.sportsapp.model.*
import com.enesaksoy.sportsapp.util.Resource
import com.google.firebase.auth.AuthResult

interface SportsRepository {

    suspend fun getNews(searchString : String) : Resource<List<NewsResponseItem>>

    suspend fun getResult(searchString : String) : Resource<List<ResultResponse>>

    suspend fun getFixture(searchString : String) : Resource<List<FixtureResponse>>

    suspend fun getTable(searchString : String) : Resource<List<TableResponseItem>>

    suspend fun insertAllNews(newsList : List<NewsResponseItem>)

    suspend fun deleteAllNews()

    suspend fun getAllNews() : List<NewsResponseItem>

    suspend fun signIn(email : String, password : String) : Resource<AuthResult>

    suspend fun signUp(email : String, password : String,userName : String) : Resource<AuthResult>
}