package com.kreator.roemah.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class EventSchedule(
    @SerializedName("idEvent")
    var eventId:String? = null,

    @SerializedName("strHomeTeam")
    var homeTeam:String? =null,

    @SerializedName("intHomeScore")
    var homeScore:String? =" ",

    @SerializedName("strAwayTeam")
    var awayTeam:String? = null,

    @SerializedName("intAwayScore")
    var awayScore:String? =" ",

    @SerializedName("dateEvent")
    var eventDate:String? = null,

    @SerializedName("strFilename")
    var fileName:String? = null

)