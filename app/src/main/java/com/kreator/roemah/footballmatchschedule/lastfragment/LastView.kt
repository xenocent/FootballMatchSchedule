package com.kreator.roemah.footballmatchschedule.lastfragment

import com.kreator.roemah.footballmatchschedule.model.EventSchedule

interface LastView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: List<EventSchedule>)
}