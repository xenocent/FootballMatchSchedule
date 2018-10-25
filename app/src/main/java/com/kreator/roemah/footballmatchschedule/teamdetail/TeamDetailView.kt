package com.kreator.roemah.footballmatchschedule.teamdetail

import com.kreator.roemah.footballmatchschedule.model.Team

interface TeamDetailView{
    fun showLoading()
    fun hideLoading()
    fun ShowTeamDetail(data:List<Team>)
}