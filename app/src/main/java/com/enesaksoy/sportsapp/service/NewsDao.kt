package com.enesaksoy.sportsapp.service

import androidx.lifecycle.LiveData
import androidx.room.*
import com.enesaksoy.sportsapp.model.NewsResponseItem

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNews(vararg news : NewsResponseItem)

    @Query("SELECT * FROM news")
    suspend fun getAllNews() : List<NewsResponseItem>

    @Query("DELETE FROM news")
    suspend fun deleteAllNews()
}