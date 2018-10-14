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

class NextFragment : Fragment(), AnkoComponent<Context>, NextView{

    private var eventNext:MutableList<EventSchedule> = mutableListOf()
    private lateinit var presentera: NextPresenter
    private lateinit var adapterNext:NextAdapter
    private lateinit var listNextEvent: RecyclerView
    private lateinit var progressBarNext: ProgressBar
    private lateinit var swipeRefreshNext: SwipeRefreshLayout


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapterNext = NextAdapter(eventNext){
            startActivity(intentFor<DetailActivity>("id" to it.eventId,"date" to it.eventDate,"file" to it.fileName).singleTop())
        }
        listNextEvent.adapter = adapterNext

        val request = ApiRepository()
        val gson = Gson()
        presentera = NextPresenter(this,request,gson, TestContextProvider())
        presentera.getNextEventList("4328")

        swipeRefreshNext.onRefresh {
            presentera.getNextEventList("4328")
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

                    listNextEvent = recyclerView {
                        id = R.id.listNextEvent
                        lparams(width= matchParent,height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }.lparams {
                        below(R.id.nextTitle)
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

}