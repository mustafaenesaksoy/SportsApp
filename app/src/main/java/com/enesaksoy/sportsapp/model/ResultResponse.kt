package com.enesaksoy.sportsapp.model


import com.google.gson.annotations.SerializedName

data class ResultResponse(
    @SerializedName("awayLogo")
    val awayLogo: String,
    @SerializedName("awayTeam")
    val awayTeam: String,
    @SerializedName("awayTeamScore")
    val awayTeamScore: String,
    @SerializedName("homeLogo")
    val homeLogo: String,
    @SerializedName("homeTeam")
    val homeTeam: String,
    @SerializedName("homeTeamScore")
    val homeTeamScore: String
)