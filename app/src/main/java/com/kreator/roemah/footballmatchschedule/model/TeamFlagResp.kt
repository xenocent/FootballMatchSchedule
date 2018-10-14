package com.kreator.roemah.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class TeamFlagResp(val teams:List<TeamFlag>)

data class TeamFlag(
        @SerializedName("strTeamBadge")
        val badge:String?=null,

        @SerializedName("idTeam")
        val teamId:String?=null
)