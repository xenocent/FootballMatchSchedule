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
import android.widget.ProgressBar
import com.google.gson.Gson
import com.kreator.roemah.footballmatchschedule.R
import com.kreator.roemah.footballmatchschedule.api.ApiRepository
import com.kreator.roemah.footballmatchschedule.detail.DetailActivity
import com.kreator.roemah.footballmatchschedule.model.EventSchedule
import com.kreator.roemah.footballmatchschedule.util.TestContextProvider
import com.kreator.roemah.footballmatchschedule.util.invisible
import com.kreator.roemah.footballmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class LastFragment : Fragment(),AnkoComponent<Context>,LastView{
    private var event: MutableList<EventSchedule> = mutableListOf()
    private lateinit var presenter: LastPresenter
    private lateinit var adapter: LastAdapter
    private lateinit var listPastEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = LastAdapter(event){
            startActivity(intentFor<DetailActivity>("id" to it.eventId,"date" to it.eventDate,"file" to it.fileName).singleTop())
        }
        listPastEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = LastPresenter(this,request,gson, TestContextProvider())

        presenter.getPastEventList("4328")

        swipeRefresh.onRefresh {
            presenter.getPastEventList("4328")
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

                    listPastEvent = recyclerView {
                        id = R.id.listPastEvent
                        lparams(width= matchParent,height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }.lparams { below(R.id.pastTitle) }

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

}