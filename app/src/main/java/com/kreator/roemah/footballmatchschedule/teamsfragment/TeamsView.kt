package com.kreator.roemah.footballmatchschedule.teamsfragment

import com.kreator.roemah.footballmatchschedule.model.LeagueList
import com.kreator.roemah.footballmatchschedule.model.Team

interface TeamsView{
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data:List<Team>)
    fun showAllTeamLeagues(data:List<LeagueList>)
}