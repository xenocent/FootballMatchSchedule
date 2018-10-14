package com.kreator.roemah.footballmatchschedule.favoritefragment

import com.kreator.roemah.footballmatchschedule.model.EventSchedule

interface FavoriteView {
    fun showLoading()
    fun hideLoading()
    fun showFavoritesList(data:List<EventSchedule>)
}