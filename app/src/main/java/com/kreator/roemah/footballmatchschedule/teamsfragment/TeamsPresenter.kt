package com.kreator.roemah.footballmatchschedule.teamsfragment

import com.google.gson.Gson
import com.kreator.roemah.footballmatchschedule.api.ApiRepository
import com.kreator.roemah.footballmatchschedule.api.TheSportDBApi
import com.kreator.roemah.footballmatchschedule.model.LeagueListResp
import com.kreator.roemah.footballmatchschedule.model.TeamResp
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.coroutines.experimental.bg

class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson): AnkoLogger {

    fun getTeamList(league:String?){
        view.showLoading()
        async (UI){
            val data = bg{
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeams(league)),
                        TeamResp::class.java)
            }

            view.apply {
                this.showTeamList(data.await().teams)
                this.hideLoading()
            }
        }
    }

    fun getAllTeamLeague(){
        async (UI){
            val data = bg{
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getAllLeague()),
                        LeagueListResp::class.java)
            }

            view.apply {
                this.showAllTeamLeagues(data.await().leagues)
            }
        }
    }
}