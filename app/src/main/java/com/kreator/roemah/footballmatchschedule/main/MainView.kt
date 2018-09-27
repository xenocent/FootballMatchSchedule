package com.kreator.roemah.footballmatchschedule.main

import com.kreator.roemah.footballmatchschedule.model.EventSchedule

interface MainView{
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data:List<EventSchedule>)
    fun showLeagueListNext(data:List<EventSchedule>)
    fun showNextDataEventList()
    fun showPastDataEventList()
}