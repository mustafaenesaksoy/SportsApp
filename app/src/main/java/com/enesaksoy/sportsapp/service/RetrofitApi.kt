package com.enesaksoy.sportsapp.service

import com.enesaksoy.sportsapp.model.*
import com.enesaksoy.sportsapp.util.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RetrofitApi {

    @GET("https://football98.p.rapidapi.com/{championship}/news")
    suspend fun getNews(
        @Path("championship") championship : String,
        @Header("X-RapidAPI-Key") apikey : String = API_KEY,
        @Header("X-RapidAPI-Host") apihost : String = API_HOST
    ) : Response<List<NewsResponseItem>>

    @GET("https://football98.p.rapidapi.com/{championship}/results")
    suspend fun getResult(
        @Path("championship") league : String,
        @Header("X-RapidAPI-Key") apiKey : String = API_KEY,
        @Header("X-RapidAPI-Host") apiHost : String = API_HOST
    ) : Response<List<Map<String, List<ResultResponse>>>>

    @GET("https://football98.p.rapidapi.com/{championship}/fixtures")
    suspend fun getFixture(
        @Path("championship") league : String,
        @Header("X-RapidAPI-Key") apiKey : String = API_KEY,
        @Header("X-RapidAPI-Host") apiHost : String = API_HOST
    ) : Response<List<Map<String, List<FixtureResponse>>>>

    @GET("https://football98.p.rapidapi.com/{championship}/table")
    suspend fun getTable(
        @Path("championship") league : String,
        @Header("X-RapidAPI-Key") apiKey : String = API_KEY,
        @Header("X-RapidAPI-Host") apiHost : String = API_HOST
    ) : Response<List<TableResponseItem>>
}