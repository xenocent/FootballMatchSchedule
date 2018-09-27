package com.kreator.roemah.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class  TeamDetail(
    @SerializedName("idTeam")
    var teamId:String? = null,

    @SerializedName("strTeamBadge")
    var teamIcon :String? = null
)