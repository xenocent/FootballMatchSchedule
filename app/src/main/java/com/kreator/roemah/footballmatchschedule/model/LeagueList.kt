package com.kreator.roemah.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class LeagueList(
        @SerializedName("idLeague")
        var idLeague : String? = null,

        @SerializedName("strLeague")
        var nameLeague:String?=null
)