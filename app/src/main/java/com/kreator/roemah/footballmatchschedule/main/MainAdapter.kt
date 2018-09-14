package com.kreator.roemah.footballmatchschedule.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.kreator.roemah.footballmatchschedule.R
import com.kreator.roemah.footballmatchschedule.model.EventSchedule
import org.jetbrains.anko.*

class MainAdapter (private val event: List<EventSchedule>):RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(EventListUI().createView(AnkoContext.create(parent.context,parent)))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(event[position])
    }
    override fun getItemCount(): Int = event.size
}

class EventListUI : AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation= LinearLayout.VERTICAL

                textView {
                    id = R.id.EventDate
                    textSize = 16f
                }.lparams(
                    width = dip(100),height = wrapContent
                )

                linearLayout {
                    lparams(width= matchParent, height = wrapContent)
                    orientation=LinearLayout.HORIZONTAL

                    textView {
                        id = R.id.TeamAway
                        textSize = 16f
                    }.lparams{margin = dip(15)}

                    textView {
                        id = R.id.TeamAwayScore
                        textSize = 16f
                    }.lparams{margin = dip(15)}

                    textView {
                        text = "VS"
                        textSize=16f
                    }.lparams{ margin = dip(15)}

                    textView {
                        id = R.id.TeamHomeScore
                        textSize = 16f
                    }.lparams{margin = dip(15)}

                    textView {
                        id = R.id.TeamHome
                        textSize = 16f
                    }.lparams{margin = dip(15)}
                }
            }
        }
    }
}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val dateEvent:TextView = view.find(R.id.EventDate)
    private val teamAway:TextView = view.find(R.id.TeamAway)
    private val teamAwayScore:TextView = view.find(R.id.TeamAwayScore)
    private val teamHome:TextView = view.find(R.id.TeamHome)
    private val teamHomeScore:TextView = view.find(R.id.TeamHomeScore)

    fun bindItem(event: EventSchedule) {
        dateEvent.text = event.eventDate
        teamAway.text = event.awayTeam
        teamAwayScore.text = event.awayScore.toString()
        teamHome.text = event.homeTeam
        teamHomeScore.text = event.homeScore.toString()
    }
}