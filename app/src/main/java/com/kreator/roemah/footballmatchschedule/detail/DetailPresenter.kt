package com.kreator.roemah.footballmatchschedule.detail

import com.google.gson.Gson
import com.kreator.roemah.footballmatchschedule.api.ApiRepository
import com.kreator.roemah.footballmatchschedule.api.TheSportDBApi
import com.kreator.roemah.footballmatchschedule.main.MainView
import com.kreator.roemah.footballmatchschedule.model.EventDetailResp
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync

class DetailPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) : AnkoLogger {

    fun getEventDetail(eventId:String?){
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getDetailEvent(eventId)),
                    EventDetailResp::class.java
            )
        }
    }
}
