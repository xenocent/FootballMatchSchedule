package com.kreator.roemah.footballmatchschedule.nextfragment

import com.kreator.roemah.footballmatchschedule.model.EventSchedule
import com.kreator.roemah.footballmatchschedule.model.LeagueList

interface NextView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueListNext(data:List<EventSchedule>)
    fun showAllLeagues(data:List<LeagueList>)
}