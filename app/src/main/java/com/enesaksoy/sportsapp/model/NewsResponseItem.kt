package com.enesaksoy.sportsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("news")
data class NewsResponseItem(
    val Image: String,
    val NewsLink: String,
    val PublisherDate: String,
    val PublisherLogo: String,
    val PublisherName: String,
    val Title: String,
    @PrimaryKey(autoGenerate = true)
    var id : Int? = 0
){

}