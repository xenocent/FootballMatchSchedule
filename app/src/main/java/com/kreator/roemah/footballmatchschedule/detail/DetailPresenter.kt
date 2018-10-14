package com.kreator.roemah.footballmatchschedule.detail

import com.google.gson.Gson
import com.kreator.roemah.footballmatchschedule.api.ApiRepository
import com.kreator.roemah.footballmatchschedule.api.TheSportDBApi
import com.kreator.roemah.footballmatchschedule.model.EventDetailResp
import com.kreator.roemah.footballmatchschedule.model.TeamFlagResp
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread

class DetailPresenter(private val view: DetailView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) : AnkoLogger {

    fun getEventDetail(eventId:String?){
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getDetailEvent(eventId)),
                    EventDetailResp::class.java
            )
           uiThread {
               view.hideLoading()
               view.showdata(data.events)
           }

        }
    }

    fun getBadgeTeam(teamId:String?,type:Int){
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getFlagTeam(teamId)),
                    TeamFlagResp::class.java
            )

            uiThread {
                view.loadImage(data.teams,type)
            }
        }
    }
}
