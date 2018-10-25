package com.kreator.roemah.footballmatchschedule.teamdetail

import com.google.gson.Gson
import com.kreator.roemah.footballmatchschedule.api.ApiRepository
import com.kreator.roemah.footballmatchschedule.api.TheSportDBApi
import com.kreator.roemah.footballmatchschedule.model.TeamResp
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamDetailPresenter (private val view: TeamDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson) {

    fun getTeamDetail(teamId:String?){
        view.showLoading()
        async (UI){
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getDetailTeam(teamId)),
                        TeamResp::class.java)
            }

            view.apply {
                this.ShowTeamDetail(data.await().teams)
                this.hideLoading()
            }
        }
    }
}