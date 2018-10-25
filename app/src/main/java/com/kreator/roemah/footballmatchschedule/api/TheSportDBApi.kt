package com.kreator.roemah.footballmatchschedule.api

import android.net.Uri
import com.kreator.roemah.footballmatchschedule.BuildConfig
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

object TheSportDBApi : AnkoLogger{

    fun getAllLeague():String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("all_leagues.php")
                .build()
                .toString()
    }
    fun getPastEvent(league: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventspastleague.php")
                .appendQueryParameter("id", league)
                .build()
                .toString()
    }

    fun getNextEvent(league: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventsnextleague.php")
                .appendQueryParameter("id", league)
                .build()
                .toString()
    }

    fun getDetailEvent(league: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("lookupevent.php")
                .appendQueryParameter("id", league)
                .build()
                .toString()
    }

    fun getFlagTeam(team:String?):String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("lookupteam.php")
                .appendQueryParameter("id", team)
                .build()
                .toString()
    }

    fun searchTeam(team:String?):String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("searchteams.php")
                .appendQueryParameter("t", team)
                .build()
                .toString()
    }

    fun searchEvent(event:String):String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("searchevents.php")
                .appendQueryParameter("e", event)
                .build()
                .toString()
    }

    fun getTeams(league: String?): String {
        return BuildConfig.BASE_URL +
                "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php?l=" + league
    }

    fun getDetailTeam(idTeam: String?): String {
        return BuildConfig.BASE_URL +
                "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + idTeam
    }

    fun getPlayerTeam(teamid:String):String{
        return BuildConfig.BASE_URL +
                "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookup_all_players.php?id=" + teamid
    }

    fun getPlayerDetail(playerId: String?): String {
        return BuildConfig.BASE_URL +
                "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupplayer.php?id=" + playerId
    }


}