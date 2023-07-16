package com.enesaksoy.sportsapp.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.enesaksoy.sportsapp.model.NewsResponseItem

@Database(entities = arrayOf(NewsResponseItem::class), version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao() : NewsDao
}