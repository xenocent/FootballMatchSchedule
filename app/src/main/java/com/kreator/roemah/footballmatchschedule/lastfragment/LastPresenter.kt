package com.kreator.roemah.footballmatchschedule.lastfragment

import com.google.gson.Gson
import com.kreator.roemah.footballmatchschedule.api.ApiRepository
import com.kreator.roemah.footballmatchschedule.api.TheSportDBApi
import com.kreator.roemah.footballmatchschedule.model.EventScheduleResp
import com.kreator.roemah.footballmatchschedule.model.LeagueListResp
import com.kreator.roemah.footballmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.coroutines.experimental.bg

class LastPresenter(private val view: LastView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) : AnkoLogger {

    fun getPastEventList(league:String?){
        view.showLoading()
        async(UI) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPastEvent(league)),
                        EventScheduleResp::class.java)
            }

            view.apply {
                this.showLeagueList(data.await().EventSchedule)
                this.hideLoading()
            }

        }
    }

    fun getAllPastLeague(){
        async (UI){
            val data = bg{
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getAllLeague()),
                        LeagueListResp::class.java)
            }

            view.apply {
                this.showAllLeagues(data.await().leagues)
            }
        }

    }
}