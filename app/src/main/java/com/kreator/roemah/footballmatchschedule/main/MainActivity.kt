package com.kreator.roemah.footballmatchschedule.main

import android.app.Fragment
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.MenuInflater
import android.view.View
import android.view.ViewManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.gson.Gson
import com.kreator.roemah.footballmatchschedule.R
import com.kreator.roemah.footballmatchschedule.R.color.colorAccent
import com.kreator.roemah.footballmatchschedule.api.ApiRepository
import com.kreator.roemah.footballmatchschedule.detail.DetailActivity
import com.kreator.roemah.footballmatchschedule.model.EventDetail
import com.kreator.roemah.footballmatchschedule.model.EventSchedule
import com.kreator.roemah.footballmatchschedule.util.invisible
import com.kreator.roemah.footballmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MainActivity : AppCompatActivity(),MainView,AnkoLogger   {

    private var event: MutableList<EventSchedule> = mutableListOf()
    private var eventNext:MutableList<EventSchedule> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var presentera: MainPresenter
    private lateinit var adapter: MainAdapter
    private lateinit var adapterNext:MainAdapter
    private lateinit var listPastEvent:RecyclerView
    private lateinit var listNextEvent:RecyclerView
    private lateinit var progressBar:ProgressBar
    private lateinit var progressBarNext:ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var swipeRefreshNext:SwipeRefreshLayout
    private lateinit var bnv_btn:BottomNavigationView
    private lateinit var fma:FrameLayout
    private lateinit var fmb:FrameLayout

//    public inline fun ViewManager.bottomNavigationView(theme: Int = 0) = bottomNavigationView(theme) {}
//    public inline fun ViewManager.bottomNavigationView(theme: Int = 0, init: BottomNavigationView.() -> Unit) = ankoView({ BottomNavigationView(it) }, theme, init)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        relativeLayout {
            lparams(width = matchParent, height = matchParent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            fma = frameLayout {
                id = Ids.past_container
                visibility = View.GONE

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout {
                        id = R.id.layout_event
                        lparams(width= matchParent,height = wrapContent)

                        listPastEvent = recyclerView {
                            lparams(width= matchParent,height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                        }

                        progressBar = progressBar{}.lparams{centerHorizontally()}
                    }
                }
            }.lparams(width = matchParent, height = matchParent) {
                above(Ids.bottom_nav)
            }

            fmb = frameLayout {
                id = Ids.next_container
                visibility = View.VISIBLE

                swipeRefreshNext = swipeRefreshLayout {
                    setColorSchemeResources(colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout {
                        id = R.id.layout_event_next
                        lparams(width= matchParent,height = wrapContent)

                        listNextEvent = recyclerView {
                            lparams(width= matchParent,height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                        }

                        progressBarNext = progressBar{}.lparams{centerHorizontally()}
                    }
                }

            }.lparams(width = matchParent, height = matchParent) {
                above(Ids.bottom_nav)
            }

            bnv_btn = bottomNavigationView {
                id = Ids.bottom_nav
                backgroundColor = android.R.color.white
                itemBackgroundResource = android.R.color.white
                itemIconTintList = ColorStateList.valueOf(R.drawable.nav_item_color_state)
                itemTextColor = ColorStateList.valueOf(R.drawable.nav_item_color_state)
                inflateMenu(R.menu.bottom_navigation_menu)

                setOnNavigationItemSelectedListener {
                    item->info(item.itemId)

                    if(item.toString().equals("Last Game")){
                        fma.visibility = View.VISIBLE
                        fmb.visibility = View.GONE
                        info {"Next Game Active" }
                    }else {
                        fma.visibility = View.GONE
                        fmb.visibility = View.VISIBLE
                        info { "Last Game Active" }
                    }
                    true
                }

            }.lparams(width = matchParent) {
                height = wrapContent
                alignParentBottom()
            }
        }

        adapter = MainAdapter(event){
            startActivity(intentFor<DetailActivity>("id" to it.eventId,"date" to it.eventDate,"file" to it.fileName).singleTop())
        }
        listPastEvent.adapter = adapter

        adapterNext = MainAdapter(eventNext){
            startActivity(intentFor<DetailActivity>("id" to it.eventId,"date" to it.eventDate,"file" to it.fileName).singleTop())
        }
        listNextEvent.adapter = adapterNext

        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this,request,gson)
        presentera = MainPresenter(this,request,gson)

        presentera.getNextEventList("4329")
        presenter.getPastEventList("4329")

        swipeRefresh.onRefresh {
            presenter.getPastEventList("4329")
        }

        swipeRefreshNext.onRefresh {
            presentera.getNextEventList("4329")
        }

    }

    private object Ids {
        val bottom_nav = 2
        val next_container = 3
        val past_container = 4
    }

    override fun showLoading() {
        progressBar.visible()
        progressBarNext.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        progressBarNext.invisible()
    }

    override fun showLeagueList(data:List<EventSchedule>) {
        swipeRefresh.isRefreshing = false
        event.clear()
        event.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showLeagueListNext(data:List<EventSchedule>) {
        swipeRefreshNext.isRefreshing = false
        eventNext.clear()
        eventNext.addAll(data)
        adapterNext.notifyDataSetChanged()
    }

    override fun showNextDataEventList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPastDataEventList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
