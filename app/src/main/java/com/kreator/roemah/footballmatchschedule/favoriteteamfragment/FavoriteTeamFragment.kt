package com.kreator.roemah.footballmatchschedule.favoriteteamfragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kreator.roemah.footballmatchschedule.R
import com.kreator.roemah.footballmatchschedule.R.color.colorAccent
import com.kreator.roemah.footballmatchschedule.db.database
import com.kreator.roemah.footballmatchschedule.model.FavoritesTeam
import com.kreator.roemah.footballmatchschedule.teamdetail.TeamDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteTeamFragment: Fragment(), AnkoComponent<Context>, AnkoLogger {
    private var favorites: MutableList<FavoritesTeam> = mutableListOf()
    private lateinit var adapter:FavoriteTeamAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteTeamAdapter(favorites){
            ctx.startActivity<TeamDetailActivity>("id" to "${it.teamId}")
        }

        listEvent.adapter = adapter
        showFavorite()
        swipeRefresh.onRefresh {
            favorites.clear()
            showFavorite()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                listEvent = recyclerView {
                    lparams (width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }

    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoritesTeam.TABLE_FAVORITE_TEAM)

            val favorite = result.parseList(classParser<FavoritesTeam>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
}