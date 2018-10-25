package com.kreator.roemah.footballmatchschedule.nextfragment

import android.support.v4.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import com.google.gson.Gson
import com.kreator.roemah.footballmatchschedule.R
import com.kreator.roemah.footballmatchschedule.api.ApiRepository
import com.kreator.roemah.footballmatchschedule.api.TheSportDBApi
import com.kreator.roemah.footballmatchschedule.detail.DetailActivity
import com.kreator.roemah.footballmatchschedule.model.EventSchedule
import com.kreator.roemah.footballmatchschedule.model.LeagueList
import com.kreator.roemah.footballmatchschedule.model.LeagueListResp
import com.kreator.roemah.footballmatchschedule.util.TestContextProvider
import com.kreator.roemah.footballmatchschedule.util.invisible
import com.kreator.roemah.footballmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.util.ArrayList

class NextFragment : Fragment(), AnkoComponent<Context>, NextView{

    private var eventNext:MutableList<EventSchedule> = mutableListOf()
    private var league : MutableList<LeagueList> = mutableListOf()
    private lateinit var presentera: NextPresenter
    private lateinit var adapterNext:NextAdapter
    private lateinit var listNextEvent: RecyclerView
    private lateinit var progressBarNext: ProgressBar
    private lateinit var swipeRefreshNext: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var leagueName:String
    private lateinit var idLeague:String
    private var sIds : List<String?> =ArrayList()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapterNext = NextAdapter(eventNext){
            startActivity(intentFor<DetailActivity>("id" to it.eventId,"date" to it.eventDate,"file" to it.fileName).singleTop())
        }
        listNextEvent.adapter = adapterNext

        val request = ApiRepository()
        val gson = Gson()
        presentera = NextPresenter(this,request,gson, TestContextProvider())

        presentera.getAllNextLeague()

        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                idLeague = sIds.get(position).toString()
                presentera.getNextEventList(idLeague)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        swipeRefreshNext.onRefresh {
            presentera.getNextEventList(idLeague)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View =with(ui){
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)
            swipeRefreshNext = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    id = R.id.layout_event_next
                    lparams(width= matchParent,height = wrapContent)

                    textView ("Next 15 Event"){
                        id = R.id.nextTitle
                        textSize = 20f
                    }.lparams(width = wrapContent,height = wrapContent){centerHorizontally()}

                    spinner = spinner(){
                        id = R.id.spinneranNext
                    }.lparams(width= matchParent,height = dip(100))

                    listNextEvent = recyclerView {
                        id = R.id.listNextEvent
                        lparams(width= matchParent,height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }.lparams {
                        below(R.id.spinneranNext)
                    }

                    progressBarNext = progressBar{}.lparams{centerHorizontally()}
                }
            }
        }
    }

    override fun showLoading() {
        progressBarNext.visible()
    }

    override fun hideLoading() {
        progressBarNext.invisible()
    }

    override fun showLeagueListNext(data: List<EventSchedule>) {
        swipeRefreshNext.isRefreshing = false
        eventNext.clear()
        eventNext.addAll(data)
        adapterNext.notifyDataSetChanged()
    }


    override fun showAllLeagues(data: List<LeagueList>) {
        league.clear()
        league.addAll(data)
        val spinnerItems = league.map {
            it.nameLeague
        }
        sIds = league.map { it.idLeague }

        val spinnerAdapter = ArrayAdapter(ctx,android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        adapterNext.notifyDataSetChanged()
    }
}