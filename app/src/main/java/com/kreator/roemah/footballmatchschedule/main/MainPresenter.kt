package com.kreator.roemah.footballmatchschedule.main

import com.google.gson.Gson
import com.kreator.roemah.footballmatchschedule.api.ApiRepository
import com.kreator.roemah.footballmatchschedule.api.TheSportDBApi
import com.kreator.roemah.footballmatchschedule.model.EventSchedule
import com.kreator.roemah.footballmatchschedule.model.EventScheduleResp
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) :AnkoLogger{

    fun getNextEventList(league:String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getNextEvent(league)),
                    EventScheduleResp::class.java
            )
            uiThread {
                view.hideLoading()
                view.showLeagueListNext(data.EventSchedule)
            }
        }
    }

    fun getPastEventList(league:String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPastEvent(league)),
                    EventScheduleResp::class.java
            )

            uiThread {
                view.hideLoading()
                view.showLeagueList(data.EventSchedule)
            }
        }
    }

    fun getLeagueList(){

    }


}