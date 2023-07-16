package com.enesaksoy.sportsapp.model

data class FixtureResponse(
    val MatchDay: String,
    val awayLogo: String,
    val awayTeam: String,
    val homeLogo: String,
    val homeTeam: String
)