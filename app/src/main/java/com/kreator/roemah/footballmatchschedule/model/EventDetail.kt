package com.kreator.roemah.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class EventDetail(
        @SerializedName("strDate")
        var eventDate:String? = null,

        @SerializedName("strHomeTeam")
        var teamHome:String? =null,

        @SerializedName("strHomeFormation")
        var teamHomeFormation:String?=null,

        @SerializedName("intHomeScore")
        var teamHomeScore:Int? = null,

        @SerializedName("strHomeGoalDetails")
        var teamHomeGoalDetail:String? =null,

        @SerializedName("intHomeShots")
        var teamHomeShots:Int? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        var homeGoalKeeper:String?=null,

        @SerializedName("strHomeLineupDefense")
        var homeDef:String? =null,

        @SerializedName("strHomeLineupMidfield")
        var homeMid:String? =null,

        @SerializedName("strHomeLineupForward")
        var homeForward:String? =null,

        @SerializedName("strHomeLineupSubstitutes")
        var homeSub:String?=null,

        @SerializedName("strAwayTeam")
        var teamAway:String? = null,

        @SerializedName("strAwayFormation")
        var teamAwayFormation:String?=null,

        @SerializedName("intAwayScore")
        var teamAwayScore: Int? = null,

        @SerializedName("strAwayGoalDetails")
        var teamAwayGoalDetail:String? = null,

        @SerializedName("intAwayShots")
        var teamAwayShots:Int? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        var awayGoalKeeper:String? =null,

        @SerializedName("strAwayLineupDefense")
        var awayDef:String? =null,

        @SerializedName("strAwayLineupMidfield")
        var awayMid:String?=null,

        @SerializedName("strAwayLineupForward")
        var awayForward:String? =null,

        @SerializedName("strAwayLineupSubstitutes")
        var awaySub:String? =null
)