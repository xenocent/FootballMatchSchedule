package com.kreator.roemah.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class EventSchedule(
//    @SerializedName("idEvent")
//    var eventId:String? = null,

    @SerializedName("strHomeTeam")
    var homeTeam:String? =null,

    @SerializedName("intHomeScore")
    var homeScore:Int? =null,

    @SerializedName("strAwayTeam")
    var awayTeam:String? = null,

    @SerializedName("intAwayScore")
    var awayScore:Int? =null,

    @SerializedName("dateEvent")
    var eventDate:String? = null

)