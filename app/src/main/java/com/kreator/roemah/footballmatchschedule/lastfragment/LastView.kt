package com.kreator.roemah.footballmatchschedule.lastfragment

import com.kreator.roemah.footballmatchschedule.model.EventSchedule
import com.kreator.roemah.footballmatchschedule.model.LeagueList

interface LastView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: List<EventSchedule>)
    fun showAllLeagues(data: List<LeagueList>)
}