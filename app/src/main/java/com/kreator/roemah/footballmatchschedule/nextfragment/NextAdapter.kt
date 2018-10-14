package com.kreator.roemah.footballmatchschedule.nextfragment

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.kreator.roemah.footballmatchschedule.R
import com.kreator.roemah.footballmatchschedule.model.EventSchedule
import org.jetbrains.anko.*

class NextAdapter (private val event: List<EventSchedule>, private val listener: (EventSchedule) -> Unit): RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(EventListUI().createView(AnkoContext.create(parent.context,parent)))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(event[position],listener)
    }
    override fun getItemCount(): Int = event.size

}

class EventListUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                topPadding = dip(10)
                bottomPadding=dip(10)
                orientation= LinearLayout.VERTICAL
                backgroundColor = Color.WHITE

                textView {
                    id = R.id.EventDate
                    textSize = 16f
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    textColor = Color.BLUE
                    bottomPadding=dip(5)
                }.lparams(
                        width = matchParent,height = wrapContent
                ){topMargin = dip(8)}

                linearLayout {
                    lparams(width= matchParent, height = wrapContent)
                    orientation= LinearLayout.HORIZONTAL

                    textView {
                        id = R.id.TeamHome
                        textSize = 16f
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }.lparams{width = dip(100);margin = dip(5)}

                    textView {
                        id = R.id.TeamHomeScore
                        textSize = 16f
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }.lparams{width = dip(25);margin = dip(5)}

                    textView {
                        text = "VS"
                        textSize=16f
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }.lparams{ width=dip(20);margin = dip(5)}

                    textView {
                        id = R.id.TeamAwayScore
                        textSize = 16f
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }.lparams{width = dip(25);margin = dip(5)}

                    textView {
                        id = R.id.TeamAway
                        textSize = 16f
                        textAlignment = View.TEXT_ALIGNMENT_CENTER

                    }.lparams{width = dip(100);margin = dip(5)}
                }
            }
        }
    }
}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val dateEvent: TextView = view.find(R.id.EventDate)
    private val teamAway: TextView = view.find(R.id.TeamAway)
    private val teamAwayScore: TextView = view.find(R.id.TeamAwayScore)
    private val teamHome: TextView = view.find(R.id.TeamHome)
    private val teamHomeScore: TextView = view.find(R.id.TeamHomeScore)

    fun bindItem(event: EventSchedule, listener:(EventSchedule)->Unit) {
        dateEvent.text = event.eventDate
        teamAway.text = event.awayTeam
        teamAwayScore.text = event.awayScore
        teamHome.text = event.homeTeam
        teamHomeScore.text = event.homeScore

        itemView.setOnClickListener{
            listener(event)
        }
    }
}