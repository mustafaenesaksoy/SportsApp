package com.enesaksoy.sportsapp.model

import com.google.gson.annotations.SerializedName


data class TableResponseItem(
    @SerializedName("Goal Difference")
    val GoalDifference: String,
    val Loosed: String,
    val Name: String,
    val Played: String,
    val Points: String,
    val Position: String,
    val SquadLogo: String,
    val Tie: String,
    val Winned: String
)