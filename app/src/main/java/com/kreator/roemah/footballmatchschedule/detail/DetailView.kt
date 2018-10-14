package com.kreator.roemah.footballmatchschedule.detail

import com.kreator.roemah.footballmatchschedule.model.EventDetail
import com.kreator.roemah.footballmatchschedule.model.TeamFlag

interface DetailView{
    fun showdata(data:List<EventDetail>)
    fun showLoading()
    fun hideLoading()
    fun loadImage(data:List<TeamFlag>,type:Int)
}
