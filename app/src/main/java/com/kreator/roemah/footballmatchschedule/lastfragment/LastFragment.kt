package com.kreator.roemah.footballmatchschedule.lastfragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
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
import com.kreator.roemah.footballmatchschedule.detail.DetailActivity
import com.kreator.roemah.footballmatchschedule.model.EventSchedule
import com.kreator.roemah.footballmatchschedule.model.LeagueList
import com.kreator.roemah.footballmatchschedule.util.TestContextProvider
import com.kreator.roemah.footballmatchschedule.util.invisible
import com.kreator.roemah.footballmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*
import java.lang.reflect.Array

class LastFragment : Fragment(),AnkoComponent<Context>,LastView,AnkoLogger{
    private var event: MutableList<EventSchedule> = mutableListOf()
    private var league:MutableList<LeagueList> = mutableListOf()
    private lateinit var presenter: LastPresenter
    private lateinit var adapter: LastAdapter
    private lateinit var spinner:Spinner
    private lateinit var listPastEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName:String
    private lateinit var idLeague:String
    private var sIds: List<String?> = ArrayList()//add ids in this list

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = LastAdapter(event){
            startActivity(intentFor<DetailActivity>("id" to it.eventId,"date" to it.eventDate,"file" to it.fileName).singleTop())
        }
        listPastEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = LastPresenter(this,request,gson, TestContextProvider())

        presenter.getAllPastLeague();

        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                idLeague = sIds.get(position).toString()
//                toast(idLeague)
                presenter.getPastEventList(idLeague)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.onRefresh {
            presenter.getPastEventList(idLeague)
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
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    id = R.id.layout_event
                    lparams(width= matchParent,height = wrapContent)

                    textView ("Past 15 Event"){
                        id = R.id.pastTitle
                        textSize = 20f
                    }.lparams(width = wrapContent,height = wrapContent){centerHorizontally()}

                    spinner = spinner(){
                        id = R.id.spinneran
                    }.lparams(width = matchParent,height = dip(100)){}

                    listPastEvent = recyclerView {
                        id = R.id.listPastEvent
                        lparams(width= matchParent,height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }.lparams { below(R.id.spinneran) }

                    progressBar = progressBar{}.lparams{centerHorizontally()}
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLeagueList(data: List<EventSchedule>) {
        swipeRefresh.isRefreshing = false
        event.clear()
        event.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showAllLeagues(data: List<LeagueList>) {
        league.clear()
        league.addAll(data)
        val spinnerItems = league.map {
            it.nameLeague
        }.toTypedArray()
        sIds = league.map {
            it.idLeague
        }
//        info { "check " + sIds }
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        adapter.notifyDataSetChanged()
    }
}