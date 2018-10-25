package com.kreator.roemah.footballmatchschedule.teamsfragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.kreator.roemah.footballmatchschedule.R
import com.kreator.roemah.footballmatchschedule.api.ApiRepository
import com.kreator.roemah.footballmatchschedule.model.LeagueList
import com.kreator.roemah.footballmatchschedule.model.Team
import com.kreator.roemah.footballmatchschedule.teamdetail.TeamDetailActivity
import com.kreator.roemah.footballmatchschedule.util.invisible
import com.kreator.roemah.footballmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamsFragment: Fragment(), AnkoComponent<Context>, TeamsView {
    private var teams: MutableList<Team> = mutableListOf()
    private var league: MutableList<LeagueList> = mutableListOf()
    private lateinit var presenter: TeamsPresenter
    private lateinit var adapter: TeamsAdapter
    private lateinit var spinner: Spinner
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = TeamsAdapter(teams)
        {
            ctx.startActivity<TeamDetailActivity>("id" to "${it.teamId}","desc" to "${it.teamDescription}")
        }
        listEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)

        presenter.getAllTeamLeague()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString().replace(" ","%20")
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)

                spinner = spinner()
                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(R.color.colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        listEvent = recyclerView {
                            lparams(width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                        }

                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showAllTeamLeagues(data: List<LeagueList>) {
        league.clear()
        league.addAll(data)
        val spinnerItems = league.map { it.nameLeague }

        val spinnerAdapter = ArrayAdapter(ctx,android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        adapter.notifyDataSetChanged()
    }
}