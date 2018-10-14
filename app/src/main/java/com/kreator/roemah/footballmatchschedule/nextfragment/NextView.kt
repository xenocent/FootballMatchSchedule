package com.kreator.roemah.footballmatchschedule.nextfragment

import com.kreator.roemah.footballmatchschedule.model.EventSchedule

interface NextView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueListNext(data:List<EventSchedule>)
}